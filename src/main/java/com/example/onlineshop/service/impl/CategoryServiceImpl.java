package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.dto.CategoryDto;
import com.example.onlineshop.model.entity.Category;
import com.example.onlineshop.repisotiry.CategoryRepository;
import com.example.onlineshop.service.CategoryService;
import com.example.onlineshop.web.exception.ObjectNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryDto> getCategoryDtos() {
        return this.categoryRepository.findAll().stream()
                .map(c -> this.modelMapper.map(c, CategoryDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Category getCategoryByName(String categoryName) {
        String name = categoryName.substring(0, 1).toUpperCase() + categoryName.substring(1);

        Optional<Category> category = this.categoryRepository.findByName(name);
        if (category.isPresent()){
            return category.get();
        }

        Category newCategory = new Category();
        newCategory.setName(categoryName);

        return this.categoryRepository.save(newCategory);
    }
}
