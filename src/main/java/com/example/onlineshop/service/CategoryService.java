package com.example.onlineshop.service;

import com.example.onlineshop.model.dto.CategoryDto;
import com.example.onlineshop.model.entity.Category;

import java.util.List;


public interface CategoryService {

    List<CategoryDto> getCategoryDtos();

    Category getCategoryByName(String categoryName);
}
