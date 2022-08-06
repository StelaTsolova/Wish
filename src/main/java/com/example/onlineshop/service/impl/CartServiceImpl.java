package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.dto.cart.CartDto;
import com.example.onlineshop.model.dto.cart.CartProductAddDto;
import com.example.onlineshop.model.dto.cart.CartProductUpdateDto;
import com.example.onlineshop.model.dto.product.ProductDto;
import com.example.onlineshop.model.entity.Cart;
import com.example.onlineshop.model.entity.CartProduct;
import com.example.onlineshop.model.entity.Product;
import com.example.onlineshop.repisotiry.CartRepository;
import com.example.onlineshop.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserEntityService userEntityService;
    private final ProductService productService;
    private final QuantityService quantityService;
    private final CartProductService cartProductService;
    private final ModelMapper modelMapper;

    public CartServiceImpl(CartRepository cartRepository, UserEntityService userEntityService,
                           ProductService productService, QuantityService quantityService,
                           CartProductService cartProductService, ModelMapper modelMapper) {
        this.cartRepository = cartRepository;
        this.userEntityService = userEntityService;
        this.productService = productService;
        this.quantityService = quantityService;
        this.cartProductService = cartProductService;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean addProduct(CartProductAddDto productAddDto, Long userId) {
        Long productId = productAddDto.getProductId();
        String productSize = productAddDto.getProductSize();

        Cart cart = this.userEntityService.getUserById(userId).getCart();
        Product product = this.productService.getProductById(productId);

        Optional<CartProduct> currentCartProduct = cart.getCartProducts().stream()
                .filter(p -> p.getSize().equals(productSize) &&
                        p.getProduct().getId().equals(productId)).findFirst();

        if (currentCartProduct.isPresent()) {
            int availableQuantity = this.quantityService.getAvailableQuantityByProductIdAndSizeName(productId, productSize);
            if(currentCartProduct.get().getQuantity() + 1 > availableQuantity){
                return false;
            }

            this.cartProductService.increaseCartProductQuantity(currentCartProduct.get().getId(), 1);
        } else {
            CartProduct newCartProduct = this.cartProductService.createCartProduct(product, productSize);
            cart.getCartProducts().add(newCartProduct);
            this.cartRepository.save(cart);
        }

        return true;
    }

    @Override
    public List<CartDto> getProducts(Long userId) {
        return this.userEntityService.getUserById(userId).getCart().getCartProducts()
                .stream().map(p -> {
                    CartDto cartDto = this.modelMapper.map(p, CartDto.class);

                    ProductDto productDto = this.modelMapper.map(p.getProduct(), ProductDto.class);
                    productDto.setImgUrl(p.getProduct().getPictures().get(0).getUrl());

                    cartDto.setProductDto(productDto);
                    cartDto.setAvailableQuantity(this.quantityService
                            .getAvailableQuantityByProductIdAndSizeName(p.getProduct().getId(), p.getSize()));

                    return cartDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void removeProduct(Long productId, Long userId) {
        this.cartProductService.removeCartProductByProductId(productId, userId);
    }

    @Override
    public void changeProductQuantity(CartProductUpdateDto cartProductUpdateDto, Long productId, Long userId) {
        this.cartProductService.changeProductQuantity( cartProductUpdateDto, productId, userId);
    }
}
