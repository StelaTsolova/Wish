package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.cart.CartDto;
import com.example.onlineshop.model.dto.cart.CartProductAddDto;
import com.example.onlineshop.model.dto.cart.CartProductUpdateDto;
import com.example.onlineshop.service.CartService;
import com.example.onlineshop.service.impl.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart/products")
    public ResponseEntity<List<CartDto>> products(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return ResponseEntity.ok(this.cartService.getProducts(userDetails.getId()));
    }

    @PostMapping("/cart/products")
    public ResponseEntity<?> addProduct(@RequestBody CartProductAddDto productAddDto,
                                        Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

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
                                            Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        this.cartService.changeProductQuantity(cartProductUpdateDto, productId, userDetails.getId());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/cart/{productId}")
    public ResponseEntity<?> removeProduct(@PathVariable Long productId, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        this.cartService.removeProduct(productId, userDetails.getId());

        return ResponseEntity.noContent().build();
    }
}
