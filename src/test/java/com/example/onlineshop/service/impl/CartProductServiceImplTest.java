package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.entity.CartProduct;
import com.example.onlineshop.model.entity.Product;
import com.example.onlineshop.repisotiry.CartProductRepository;
import com.example.onlineshop.service.UserEntityService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CartProductServiceImplTest {

    private CartProductServiceImpl serviceTest;
    private Product productTest;

    @Mock
    private CartProductRepository cartProductRepositoryMock;
    @Mock
    private UserEntityService userEntityServiceMock;

    @BeforeEach
    void init() {
        this.serviceTest = new CartProductServiceImpl(cartProductRepositoryMock, userEntityServiceMock);

        this.productTest = new Product();
    }

    @Test
    void createCartProductShouldReturnCartProduct() {
        CartProduct cartProduct = serviceTest.createCartProduct(productTest, "S");

        Assertions.assertEquals(cartProduct.getProduct(), productTest);
        Assertions.assertEquals(cartProduct.getQuantity(), 1);
        Assertions.assertEquals(cartProduct.getSize(), "S");
    }
}
