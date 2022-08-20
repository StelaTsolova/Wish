package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.entity.Picture;
import com.example.onlineshop.model.entity.Product;
import com.example.onlineshop.repisotiry.ProductRepository;
import com.example.onlineshop.service.CategoryService;
import com.example.onlineshop.service.CloudinaryService;
import com.example.onlineshop.service.ProductService;
import com.example.onlineshop.service.QuantityService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ProductServiceImplTest {

    private ProductService productServiceTest;
    private Product productTest;

    @Mock
    private ProductRepository productRepositoryMock;

    @Mock
    private QuantityService quantityServiceMock;

    @Mock
    private CategoryService categoryServiceMock;

    @Mock
    private CloudinaryService cloudinaryServiceMock;

    @Mock
    private ModelMapper modelMapperMock;

    @BeforeEach
    void init() {
        this.productServiceTest = new ProductServiceImpl(this.productRepositoryMock, this.quantityServiceMock,
                this.categoryServiceMock, this.cloudinaryServiceMock, this.modelMapperMock);

        this.productTest = new Product();
        this.productTest.setId(1L);
        this.productTest.setPictures(new ArrayList<>());
    }

    @Test
    void addPictureByProductIdTest() {
        Picture picture = new Picture();

        Mockito.when(this.productRepositoryMock.findById(this.productTest.getId()))
                .thenReturn(Optional.ofNullable(this.productTest));

        Assertions.assertEquals(this.productTest.getPictures().size(), 0);

        this.productServiceTest.addPictureByProductId(this.productTest.getId(), picture);

        Assertions.assertEquals(this.productTest.getPictures().size(), 1);
    }

    @Test
    void removePictureTest() {
        Picture picture = new Picture();
        picture.setProduct(this.productTest);

        this.productTest.getPictures().add(picture);

        Mockito.when(this.productRepositoryMock.findById(this.productTest.getId()))
                .thenReturn(Optional.ofNullable(this.productTest));

        Assertions.assertEquals(this.productTest.getPictures().size(), 1);

        this.productServiceTest.removePicture(picture);

        Assertions.assertEquals(this.productTest.getPictures().size(), 0);
    }
}
