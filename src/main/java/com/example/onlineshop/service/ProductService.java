package com.example.onlineshop.service;

import com.example.onlineshop.model.dto.product.ProductCreateDto;
import com.example.onlineshop.model.dto.product.ProductDetailsDto;
import com.example.onlineshop.model.dto.product.ProductDto;
import com.example.onlineshop.model.entity.Picture;
import com.example.onlineshop.model.entity.Product;

import java.util.List;

public interface ProductService {

   List<ProductDto> getProductByCategoryDtos(String categoryName);

    Product getProductById(Long productId);

    ProductDetailsDto getProductDetailsDtoById(Long id);

    Long saveProduct(ProductCreateDto productCreateDto);

    void addPictureByProductId(Long productId, Picture picture);

    void removePicture(Picture picture);

    void updateProduct(Long productId, ProductCreateDto productCreateDto);

    void deleteProduct(Long id);
}
