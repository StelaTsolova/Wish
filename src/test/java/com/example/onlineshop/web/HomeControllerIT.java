package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.CategoryDto;
import com.example.onlineshop.model.entity.Category;
import com.example.onlineshop.repisotiry.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIT {

    private static final String CATEGORY_NAME_1 = "first";
    private static final String CATEGORY_NAME_2 = "second";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        this.categoryRepository.deleteAll();
    }

    @Test
    void categoriesTest() throws Exception {
        initCategories();

        this.mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].name", is(CATEGORY_NAME_1)))
                .andExpect(jsonPath("$.[1].name", is(CATEGORY_NAME_2)));
    }

     void initCategories() {
        Category categoryFirst = new Category();
        categoryFirst.setName(CATEGORY_NAME_1);
        this.categoryRepository.save(categoryFirst);

        Category categorySecond = new Category();
        categorySecond.setName(CATEGORY_NAME_2);
        this.categoryRepository.save(categorySecond);
    }
}
