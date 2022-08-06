package com.example.onlineshop.service;

import com.example.onlineshop.model.dto.OrderCreateDto;
import com.example.onlineshop.service.impl.UserDetailsImpl;

public interface OrderService {

    void createOrder(OrderCreateDto orderCreateDto, UserDetailsImpl userDetails);
}
