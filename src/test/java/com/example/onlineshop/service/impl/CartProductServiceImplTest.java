package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.dto.cart.CartProductUpdateDto;
import com.example.onlineshop.model.entity.Cart;
import com.example.onlineshop.model.entity.CartProduct;
import com.example.onlineshop.model.entity.Product;
import com.example.onlineshop.model.entity.UserEntity;
import com.example.onlineshop.repisotiry.CartProductRepository;
import com.example.onlineshop.service.CartProductService;
import com.example.onlineshop.service.UserEntityService;
import com.example.onlineshop.web.exception.ObjectNotFoundException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.verification.VerificationMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CartProductServiceImplTest {

    private CartProductService serviceTest;
    private CartProduct cartProductTest;
    private Product productTest;

    @Mock
    private CartProductRepository cartProductRepositoryMock;

    @Mock
    private UserEntityService userEntityServiceMock;

    @BeforeEach
    void init() {
        this.serviceTest = new CartProductServiceImpl(this.cartProductRepositoryMock, this.userEntityServiceMock);

        this.productTest = new Product();

        this.cartProductTest = new CartProduct();
        this.cartProductTest.setId(1L);
        this.cartProductTest.setProduct(this.productTest);
        this.cartProductTest.setQuantity(1);
        this.cartProductTest.setSize("S");
    }

    @Test
    public void createCartProductShouldReturnCorrectCartProduct() {
        Mockito.when(this.cartProductRepositoryMock.save(Mockito.any()))
                .thenReturn(this.cartProductTest);

        CartProduct cartProduct = this.serviceTest.createCartProduct(this.productTest, "S");

        Assertions.assertEquals(cartProduct.getProduct(), this.productTest);
        Assertions.assertEquals(cartProduct.getQuantity(), 1);
        Assertions.assertEquals(cartProduct.getSize(), "S");
    }

    @Test
    public void increaseCartProductQuantityShouldThrowWhenCartProductNotFound() {
        Mockito.when(this.cartProductRepositoryMock.findById(1L))
                .thenReturn(Optional.empty());

        Assertions.assertThrows(ObjectNotFoundException.class, () -> this.serviceTest.increaseCartProductQuantity(1L, 1));
    }

    @Test
    public void increaseCartProductQuantityShouldIncreaseQuantity() {
        Mockito.when(this.cartProductRepositoryMock.findById(1L))
                .thenReturn(Optional.of(this.cartProductTest));

        Assertions.assertEquals(this.cartProductTest.getQuantity(), 1);
        this.serviceTest.increaseCartProductQuantity(1L, 1);

        Assertions.assertEquals(this.cartProductTest.getQuantity(), 2);
    }

    @Test
    public void removeCartProductByProductIdShouldRemove() {
        Mockito.when(this.userEntityServiceMock.getUserById(1L))
                .thenReturn(getUserEntityTest());
        this.serviceTest.removeCartProductByProductId(1L, 1L);

        Mockito.verify(this.cartProductRepositoryMock, times(1)).delete(this.cartProductTest);
    }

    @Test
    public void changeProductQuantityShouldChangeQuantity() {
        CartProductUpdateDto cartProductUpdateDto = new CartProductUpdateDto();
        cartProductUpdateDto.setQuantity(3);

        Mockito.when(this.userEntityServiceMock.getUserById(1L))
                .thenReturn(getUserEntityTest());
        Mockito.when(this.cartProductRepositoryMock.save(Mockito.any()))
                .thenReturn(this.cartProductTest);

        Assertions.assertEquals(this.cartProductTest.getQuantity(), 1);
        this.serviceTest.changeProductQuantity(cartProductUpdateDto, 1L, 1L);

        Assertions.assertEquals(this.cartProductTest.getQuantity(), 3);
    }

    private UserEntity getUserEntityTest() {
        List cartProducts = new ArrayList();
        cartProducts.add(this.cartProductTest);

        Cart cart = new Cart();

        cart.setCartProducts(cartProducts);

        UserEntity userEntity = new UserEntity();
        userEntity.setCart(cart);

        return userEntity;
    }
}
