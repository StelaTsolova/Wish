package com.example.onlineshop.service;

import com.example.onlineshop.model.dto.cart.CartDto;
import com.example.onlineshop.model.dto.cart.CartProductAddDto;
import com.example.onlineshop.model.dto.cart.CartProductUpdateDto;

import java.util.List;

public interface CartService {

    boolean addProduct(CartProductAddDto cartProductAddDto, Long userId);

    List<CartDto> getProducts(Long userId);

    void removeProduct(Long productId, Long userId);

    void changeProductQuantity(CartProductUpdateDto cartProductUpdateDto, Long productId, Long userId);
}
