package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.CategoryDto;
import com.example.onlineshop.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HomeController {

    private final CategoryService categoryService;

    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryDto>> categories(){
        return ResponseEntity.ok(this.categoryService.getCategoryDtos());
    }
}
