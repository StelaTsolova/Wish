package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.dto.OrderCreateDto;
import com.example.onlineshop.model.entity.CartProduct;
import com.example.onlineshop.model.entity.Order;
import com.example.onlineshop.model.entity.UserEntity;
import com.example.onlineshop.repisotiry.OrderRepository;
import com.example.onlineshop.service.CartService;
import com.example.onlineshop.service.OrderService;
import com.example.onlineshop.service.UserEntityService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserEntityService userEntityService;
    private final ModelMapper modelMapper;

    public OrderServiceImpl(OrderRepository orderRepository, UserEntityService userEntityService,
                            ModelMapper modelMapper) {
        this.orderRepository = orderRepository;
        this.userEntityService = userEntityService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createOrder(OrderCreateDto orderCreateDto, UserDetailsImpl userDetails) {
        UserEntity userEntity = this.userEntityService.getUserById(userDetails.getId());

        List<CartProduct> products = new ArrayList<>(userEntity.getCart().getCartProducts());

        Order order = this.modelMapper.map(orderCreateDto, Order.class);
        order.setOwner(userEntity);
        order.setProducts(products);
        order.setCreated(LocalDateTime.now());

        this.orderRepository.save(order);
        this.userEntityService.clearCart(userDetails.getId());
    }
}
