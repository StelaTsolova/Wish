package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.OrderCreateDto;
import com.example.onlineshop.model.entity.Picture;
import com.example.onlineshop.service.CloudinaryImage;
import com.example.onlineshop.service.OrderService;
import com.example.onlineshop.service.impl.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

import static com.example.onlineshop.web.UserRegisterController.getErrorMessages;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderCreateDto orderCreateDto,
                                         BindingResult bindingResult, Authentication authentication) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getErrorMessages(bindingResult.getAllErrors()));
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        this.orderService.createOrder(orderCreateDto, userDetails);

        return ResponseEntity.ok().build();
    }
}
