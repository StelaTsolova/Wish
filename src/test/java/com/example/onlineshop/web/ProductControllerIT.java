package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.product.ProductCreateDto;
import com.example.onlineshop.model.entity.Category;
import com.example.onlineshop.model.entity.Product;
import com.example.onlineshop.model.entity.Quantity;
import com.example.onlineshop.model.entity.Size;
import com.example.onlineshop.repisotiry.CategoryRepository;
import com.example.onlineshop.repisotiry.ProductRepository;
import com.example.onlineshop.repisotiry.QuantityRepository;
import com.example.onlineshop.repisotiry.SizeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductControllerIT {

    private static final String CATEGORY_NAME_1 = "categoryFirst";
    private static final String CATEGORY_NAME_2 = "categorySecond";
    private static final String PRODUCT_IMG_URL = "https://res.cloudinary.com/dj0dxejrk/image/upload/v1660579108/wish_c5gw2r.png";
    private static final String PRODUCT_NAME_1 = "nameFirst";
    private static final String PRODUCT_NAME_2 = "nameSecond";
    private static final String PRODUCT_NAME_3 = "nameThird";
    private static final BigDecimal PRODUCT_PRICE_1 = BigDecimal.valueOf(1);
    private static final BigDecimal PRODUCT_PRICE_2 = BigDecimal.valueOf(2);
    private static final BigDecimal PRODUCT_PRICE_3 = BigDecimal.valueOf(3);
    private static final String PRODUCT_MATERIAL_1 = "materialFirst";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuantityRepository quantityRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Test
    void getProductsByCategoryNameTest() throws Exception {
        initProducts();

        this.mockMvc.perform(get("/products/category").param("name", CATEGORY_NAME_1))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].imgUrl", is(PRODUCT_IMG_URL)))
                .andExpect(jsonPath("$.[0].name", is(PRODUCT_NAME_1)))
                .andExpect(jsonPath("$.[0].price", is(PRODUCT_PRICE_1.doubleValue())))
                .andExpect(jsonPath("$.[1].id", is(3)))
                .andExpect(jsonPath("$.[1].imgUrl", is(PRODUCT_IMG_URL)))
                .andExpect(jsonPath("$.[1].name", is(PRODUCT_NAME_3)))
                .andExpect(jsonPath("$.[1].price", is(PRODUCT_PRICE_3.doubleValue())));
    }

    @Test
    void getProductTest() throws Exception {
        initProducts();

        this.mockMvc.perform(get("/products/{id}", 1L))
                .andExpect(jsonPath("$.name", is(PRODUCT_NAME_1)))
                .andExpect(jsonPath("$.price", is(PRODUCT_PRICE_1.doubleValue())))
                .andExpect(jsonPath("$.material", is(PRODUCT_MATERIAL_1)))
                .andExpect(jsonPath("$.quantities.[0].availableQuantity", is(2)))
                .andExpect(jsonPath("$.quantities.[0].sizeName", is("S")))
                .andExpect(jsonPath("$.quantities.[1].availableQuantity", is(1)))
                .andExpect(jsonPath("$.quantities.[1].sizeName", is("M")));
    }

//    @Test
//    @WithMockUser(value = "admin", roles = {"ADMIN"})
//    void createProductTest() throws Exception {
//        initCategories();
//        ProductCreateDto productCreateDto = new ProductCreateDto();
//        productCreateDto.setName(PRODUCT_NAME_1);
//        productCreateDto.setPrice(PRODUCT_PRICE_1);
//        productCreateDto.setMaterial(PRODUCT_MATERIAL_1);
//        productCreateDto.setCategoryName(CATEGORY_NAME_1);
//        productCreateDto.setQuantities(Map.of("S", 2, "M", 1));
//
//        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/products/create")
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .content(this.mapper.writeValueAsString(productCreateDto));
//
//
//        this.mockMvc.perform(mockRequest)
//                .andExpect(status().isOk());
//    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void deleteProductTest() throws Exception {
        initProducts();

        this.mockMvc.perform(delete("/products").param("id", "1"))
                .andExpect(status().isNoContent());
    }

    void initProducts() {
        initCategories();

        Product productFirst = new Product();
        productFirst.setName(PRODUCT_NAME_1);
        productFirst.setPrice(PRODUCT_PRICE_1);
        productFirst.setMaterial(PRODUCT_MATERIAL_1);
        productFirst.setCategory(this.categoryRepository.findByName(CATEGORY_NAME_1).get());
        productFirst = this.productRepository.save(productFirst);
        initQuantity(productFirst, 2, "S");
        initQuantity(productFirst, 1, "M");
        this.productRepository.save(productFirst);

        Product productSecond = new Product();
        productSecond.setName(PRODUCT_NAME_2);
        productSecond.setPrice(PRODUCT_PRICE_2);
        productSecond.setCategory(this.categoryRepository.findByName(CATEGORY_NAME_2).get());
        this.productRepository.save(productSecond);

        Product productThird = new Product();
        productThird.setName(PRODUCT_NAME_3);
        productThird.setPrice(PRODUCT_PRICE_3);
        productThird.setCategory(this.categoryRepository.findByName(CATEGORY_NAME_1).get());
        this.productRepository.save(productThird);
    }

    void initCategories() {
        Category categoryFirst = new Category();
        categoryFirst.setName(CATEGORY_NAME_1);
        this.categoryRepository.save(categoryFirst);

        Category categorySecond = new Category();
        categorySecond.setName(CATEGORY_NAME_2);
        this.categoryRepository.save(categorySecond);
    }
    //TODO create initProduct()

    void initQuantity(Product product, int availableQuantity, String sizeName) {
        Quantity quantity = new Quantity();
        quantity.setProduct(product);
        quantity.setAvailableQuantity(availableQuantity);
        quantity.setSize(initSize(sizeName));

        this.quantityRepository.save(quantity);
    }

    Size initSize(String name) {
        Size size = new Size();
        size.setName(name);

        return this.sizeRepository.save(size);
    }
}
