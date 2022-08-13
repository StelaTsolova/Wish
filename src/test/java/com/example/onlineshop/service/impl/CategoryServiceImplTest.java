package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.dto.cart.CartProductAddDto;
import com.example.onlineshop.model.entity.CartProduct;
import com.example.onlineshop.model.entity.Category;
import com.example.onlineshop.model.entity.Product;
import com.example.onlineshop.repisotiry.CartRepository;
import com.example.onlineshop.repisotiry.CategoryRepository;
import com.example.onlineshop.service.CategoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    private CategoryService serviceTest;
    private Category categoryTest;

    @Mock
    private CategoryRepository categoryRepositoryMock;
    @Mock
    private ModelMapper modelMapperMock;

    @BeforeEach
    void init() {
        this.serviceTest = new CategoryServiceImpl(this.categoryRepositoryMock, this.modelMapperMock);

        this.categoryTest = new Category();
    }

    @Test
    public void getCategoryByNameShouldReturnCategoryWhenExist() {
        Mockito.when(this.categoryRepositoryMock.findByName("Shoes")).thenReturn(Optional.of(this.categoryTest));

        Assertions.assertEquals(this.serviceTest.getCategoryByName("Shoes"), this.categoryTest);
    }

    @Test
    public void getCategoryByNameShouldReturnNewCategoryWhenNotExist() {
        this.serviceTest.getCategoryByName("Shoes");

        Mockito.verify(this.categoryRepositoryMock, times(1)).save(Mockito.any());
    }
}
