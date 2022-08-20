package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.dto.OrderCreateDto;
import com.example.onlineshop.model.entity.Cart;
import com.example.onlineshop.model.entity.CartProduct;
import com.example.onlineshop.model.entity.Order;
import com.example.onlineshop.model.entity.UserEntity;
import com.example.onlineshop.repisotiry.OrderRepository;
import com.example.onlineshop.service.OrderService;
import com.example.onlineshop.service.UserEntityService;
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
public class OrderServiceImplTest {

    private OrderService serviceTest;
    private CartProduct cartProductTest;

    @Mock
    private OrderRepository orderRepositoryMock;

    @Mock
    private UserEntityService userEntityServiceMock;

    @Mock
    private ModelMapper modelMapperMock;

    @Mock
    private UserDetailsImpl userDetailsMock;

    @BeforeEach
    void init() {
        this.serviceTest = new OrderServiceImpl(this.orderRepositoryMock, this.userEntityServiceMock,
                this.modelMapperMock);

        this.cartProductTest = new CartProduct();
    }

    @Test
    public void createOrderTest() {
        OrderCreateDto orderCreateDto = new OrderCreateDto();
        Order order = new Order();

        Mockito.when(this.userEntityServiceMock.getUserById(this.userDetailsMock.getId()))
                .thenReturn(getUserEntityTest());
        Mockito.when(this.modelMapperMock.map(orderCreateDto, Order.class))
                .thenReturn(order);

        this.serviceTest.createOrder(orderCreateDto, this.userDetailsMock);

        Mockito.when(this.userDetailsMock.getId())
                .thenReturn(0L);

        Mockito.verify(this.orderRepositoryMock, times(1)).save(order);
        Mockito.verify(this.userEntityServiceMock, times(1)).clearCart(this.userDetailsMock.getId());
    }

    private UserEntity getUserEntityTest() {
        List cartProducts = new ArrayList();
        cartProducts.add(this.cartProductTest);

        Cart cart = new Cart();

        cart.setCartProducts(cartProducts);

        UserEntity userEntity = new UserEntity();
        userEntity.setId(0L);
        userEntity.setCart(cart);

        return userEntity;
    }

}
