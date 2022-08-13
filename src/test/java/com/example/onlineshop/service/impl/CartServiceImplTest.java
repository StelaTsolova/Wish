package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.dto.cart.CartProductAddDto;
import com.example.onlineshop.model.dto.cart.CartProductUpdateDto;
import com.example.onlineshop.model.entity.Cart;
import com.example.onlineshop.model.entity.CartProduct;
import com.example.onlineshop.model.entity.Product;
import com.example.onlineshop.model.entity.UserEntity;
import com.example.onlineshop.repisotiry.CartRepository;
import com.example.onlineshop.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CartServiceImplTest {

    private CartService serviceTest;
    private Product product;
    private CartProduct cartProductTest;

    @Mock
    private CartRepository cartRepositoryMock;
    @Mock
    private UserEntityService userEntityServiceMock;
    @Mock
    private ProductService productServiceMock;
    @Mock
    private QuantityService quantityServiceMock;
    @Mock
    private CartProductService cartProductServiceMock;
    @Mock
    private ModelMapper modelMapperMock;

    @BeforeEach
    void init() {
        this.serviceTest = new CartServiceImpl(this.cartRepositoryMock, this.userEntityServiceMock, this.productServiceMock,
                this.quantityServiceMock, this.cartProductServiceMock, this.modelMapperMock);

        this.product = new Product();
        product.setId(1L);

        this.cartProductTest = new CartProduct();
        this.cartProductTest.setId(1L);
        this.cartProductTest.setProduct(this.product);
        this.cartProductTest.setQuantity(1);
        this.cartProductTest.setSize("S");
    }

    @Test
    public void addProductShouldReturnFalseWhenProductPresentAndNotAvailableQuantity() {
        CartProductAddDto cartProductAddDto = new CartProductAddDto();
        cartProductAddDto.setProductId(1L);
        cartProductAddDto.setProductSize("S");

        Mockito.when(this.userEntityServiceMock.getUserById(1L))
                .thenReturn(getUserEntityTest());
        Mockito.when(this.productServiceMock.getProductById(1L))
                .thenReturn(this.product);
        Mockito.when(this.quantityServiceMock.getAvailableQuantityByProductIdAndSizeName(1L, "S"))
                .thenReturn(1);

        Assertions.assertFalse(this.serviceTest.addProduct(cartProductAddDto, 1L));
    }

    @Test
    public void addProductShouldReturnTrueWhenProductPresentAndHaveAvailableQuantityAndIncreaseQuantity() {
        CartProductAddDto cartProductAddDto = new CartProductAddDto();
        cartProductAddDto.setProductId(1L);
        cartProductAddDto.setProductSize("S");

        Mockito.when(this.userEntityServiceMock.getUserById(1L))
                .thenReturn(getUserEntityTest());
        Mockito.when(this.productServiceMock.getProductById(1L))
                .thenReturn(this.product);
        Mockito.when(this.quantityServiceMock.getAvailableQuantityByProductIdAndSizeName(1L, "S"))
                .thenReturn(2);


        Assertions.assertTrue(this.serviceTest.addProduct(cartProductAddDto, 1L));
        Mockito.verify(this.cartProductServiceMock, times(1))
                .increaseCartProductQuantity(this.cartProductTest.getId(), 1);
    }

    @Test
    public void addProductShouldReturnTrueWhenProductNotPresentAndHaveAvailableQuantityAndCreateCartProductAndAddToCart() {
        CartProductAddDto cartProductAddDto = new CartProductAddDto();
        cartProductAddDto.setProductId(1L);
        cartProductAddDto.setProductSize("M");

        UserEntity userEntityTest = getUserEntityTest();
        CartProduct newCartProduct = new CartProduct();

        Mockito.when(this.userEntityServiceMock.getUserById(1L))
                .thenReturn(userEntityTest);
        Mockito.when(this.productServiceMock.getProductById(1L))
                .thenReturn(this.product);
        Mockito.when(this.cartProductServiceMock.createCartProduct(this.product, "M"))
                .thenReturn(newCartProduct);

        Assertions.assertEquals(userEntityTest.getCart().getCartProducts().size(), 1);
        Assertions.assertTrue(this.serviceTest.addProduct(cartProductAddDto, 1L));
        Assertions.assertEquals(userEntityTest.getCart().getCartProducts().size(), 2);
        Mockito.verify(this.cartRepositoryMock, times(1)).save(userEntityTest.getCart());
    }

    @Test
    public void removeProduct() {
        this.serviceTest.removeProduct(1L, 1L);
        Mockito.verify(this.cartProductServiceMock, times(1))
                .removeCartProductByProductId(1L, 1L);
    }

    @Test
    public void changeProductQuantity() {
       CartProductUpdateDto cartProductUpdateDto = new CartProductUpdateDto();

        this.serviceTest.changeProductQuantity(cartProductUpdateDto, 1L, 1L);
        Mockito.verify(this.cartProductServiceMock, times(1))
                .changeProductQuantity(cartProductUpdateDto, 1L, 1L);
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
