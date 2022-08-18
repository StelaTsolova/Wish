package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.product.ProductCreateDto;
import com.example.onlineshop.model.dto.product.ProductDetailsDto;
import com.example.onlineshop.model.dto.product.ProductDto;
import com.example.onlineshop.service.ProductService;
import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

import static com.example.onlineshop.web.UserRegisterController.getErrorMessages;

@RestController
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products/category")
    public ResponseEntity<List<ProductDto>> getProductsByCategoryName(@RequestParam(value="name") String categoryName){
        return ResponseEntity.ok(this.productService.getProductByCategoryDtos(categoryName));
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<ProductDetailsDto> getProduct(@PathVariable Long id){
        return ResponseEntity.ok(this.productService.getProductDetailsDtoById(id));
    }

    @PostMapping("/products/create")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> createProduct(@RequestBody @Valid ProductCreateDto productCreateDto,
                                           BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getErrorMessages(bindingResult.getAllErrors()));
        }

        Long productId = this.productService.saveProduct(productCreateDto);

        return ResponseEntity.ok().body(Map.of("productId", productId));
    }

    @PutMapping("/products/edit")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> editProduct(@RequestParam(value = "productId") Long productId,
                                         @RequestBody @Valid ProductCreateDto productCreateDto,
                                           BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(getErrorMessages(bindingResult.getAllErrors()));
        }

        this.productService.updateProduct(productId, productCreateDto);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/products")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> deleteProduct(@RequestParam(value = "id") Long id){
        this.productService.deleteProduct(id);

        return ResponseEntity.noContent().build();
    }
}
