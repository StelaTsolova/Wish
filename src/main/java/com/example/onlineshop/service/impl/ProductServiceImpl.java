package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.dto.product.ProductCreateDto;
import com.example.onlineshop.model.dto.product.ProductDetailsDto;
import com.example.onlineshop.model.dto.product.ProductDto;
import com.example.onlineshop.model.dto.QuantityDto;
import com.example.onlineshop.model.entity.Picture;
import com.example.onlineshop.model.entity.Product;
import com.example.onlineshop.model.entity.Quantity;
import com.example.onlineshop.repisotiry.ProductRepository;
import com.example.onlineshop.service.*;
import com.example.onlineshop.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final QuantityService quantityService;
    private final CategoryService categoryService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    public ProductServiceImpl(ProductRepository productRepository, QuantityService quantityService,
                              CategoryService categoryService, CloudinaryService cloudinaryService,
                              ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.quantityService = quantityService;
        this.categoryService = categoryService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ProductDto> getProductByCategoryDtos(String categoryName) {
        return this.productRepository.findAllByCategory_Name(categoryName).stream()
                .map(p -> {
                    ProductDto productDto = this.modelMapper.map(p, ProductDto.class);
                    if(p.getPictures().isEmpty()){
                        productDto.setImgUrl("https://res.cloudinary.com/dj0dxejrk/image/upload/v1660579108/wish_c5gw2r.png");
                    } else {
                        productDto.setImgUrl(p.getPictures().get(0).getUrl());
                    }

                    return productDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Product getProductById(Long productId) {
        return this.productRepository.findById(productId)
                .orElseThrow(() -> new ObjectNotFoundException("Product with id " + productId + " is not found!"));
    }

    @Override
    public ProductDetailsDto getProductDetailsDtoById(Long id) {
        Product product = getProductById(id);
        List<Quantity> quantities = this.quantityService.getQuantitiesByProductId(id);

        List<QuantityDto> quantityDtos = quantities.stream().map(q -> {
            QuantityDto quantityDto = new QuantityDto();
            quantityDto.setAvailableQuantity(q.getAvailableQuantity());
            quantityDto.setSizeName(q.getSize().getName());

            return quantityDto;
        }).toList();

        ProductDetailsDto productDetailsDto = this.modelMapper.map(product, ProductDetailsDto.class);
        productDetailsDto.setImgUrls(product.getPictures().stream().map(Picture::getUrl).collect(Collectors.toList()));
        productDetailsDto.setQuantities(quantityDtos);

        return productDetailsDto;
    }

    @Override
    public Long saveProduct(ProductCreateDto productCreateDto) {
        Product product = this.modelMapper.map(productCreateDto, Product.class);
        product.setCategory(this.categoryService.getCategoryByName(productCreateDto.getCategoryName()));
        product = this.productRepository.save(product);

        product.setQuantities(this.quantityService.saveQuantities(productCreateDto.getQuantities(), product));
        product.setAdded(LocalDate.now());

        return this.productRepository.save(product).getId();
    }

    @Override
    public void addPictureByProductId(Long productId, Picture picture) {
        Product product = getProductById(productId);
        product.getPictures().add(picture);

        this.productRepository.save(product);
    }

    @Override
    public void removePicture(Picture picture) {
        Product product = getProductById(picture.getProduct().getId());
        product.getPictures().remove(picture);

        this.productRepository.save(product);
    }

    @Override
    public void updateProduct(Long productId, ProductCreateDto productCreateDto) {
        Product product = getProductById(productId);
        product.setName(productCreateDto.getName());
        product.setPrice(productCreateDto.getPrice());
        product.setMaterial(productCreateDto.getMaterial());
        product.setQuantities(this.quantityService.updateQuantities(productCreateDto.getQuantities(), product));

        this.productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = getProductById(id);
        product.getPictures().forEach(p -> this.cloudinaryService.delete(p.getPublicId()));

        this.productRepository.delete(product);
    }
}
