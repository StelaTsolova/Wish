package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.cart.CartDto;
import com.example.onlineshop.model.dto.cart.CartProductAddDto;
import com.example.onlineshop.model.dto.cart.CartProductUpdateDto;
import com.example.onlineshop.service.CartService;
import com.example.onlineshop.service.impl.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart/products")
    public ResponseEntity<List<CartDto>> products(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok(this.cartService.getProducts(userDetails.getId()));
    }

    @PostMapping("/cart/products")
    public ResponseEntity<?> addProduct(@RequestBody CartProductAddDto productAddDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boolean isAdded = this.cartService.addProduct(productAddDto, userDetails.getId());

        if (isAdded) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @PatchMapping("/cart/{productId}")
    public ResponseEntity<?> updateQuantity(@PathVariable Long productId,
                                            @RequestBody CartProductUpdateDto cartProductUpdateDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        this.cartService.changeProductQuantity(cartProductUpdateDto, productId, userDetails.getId());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<?> removeProduct(@PathVariable Long productId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        this.cartService.removeProduct(productId, userDetails.getId());

        return ResponseEntity.noContent().build();
    }
}
