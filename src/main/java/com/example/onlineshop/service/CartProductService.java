package com.example.onlineshop.service;

import com.example.onlineshop.model.dto.cart.CartProductUpdateDto;
import com.example.onlineshop.model.entity.CartProduct;
import com.example.onlineshop.model.entity.Product;

public interface CartProductService {

    CartProduct createCartProduct(Product product, String size);

    void increaseCartProductQuantity(Long id, int number);

    void removeCartProductByProductId(Long productId, Long userId);

    void changeProductQuantity(CartProductUpdateDto cartProductUpdateDto, Long productId, Long userId);
}
