package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.cart.CartProductAddDto;
import com.example.onlineshop.model.dto.cart.CartProductUpdateDto;
import com.example.onlineshop.model.entity.*;
import com.example.onlineshop.model.enums.ERole;
import com.example.onlineshop.repisotiry.*;
import com.example.onlineshop.service.impl.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CartControllerIT {

    private static final String USER_ENTITY_FIRST_NAME = "Pesho";
    private static final String USER_ENTITY_EMAIL = "pesho@gmail.com";
    private static final String USER_ENTITY_PASSWORD = "12345678";
    private static final String CATEGORY_NAME = "categoryFirst";
    private static final String PRODUCT_IMG_URL = "https://res.cloudinary.com/dj0dxejrk/image/upload/v1660579108/wish_c5gw2r.png";
    private static final String PRODUCT_NAME_1 = "nameFirst";
    private static final String PRODUCT_NAME_2 = "nameSecond";
    private static final BigDecimal PRODUCT_PRICE_1 = BigDecimal.valueOf(1);
    private static final BigDecimal PRODUCT_PRICE_2 = BigDecimal.valueOf(2);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private CartProductRepository cartProductRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuantityRepository quantityRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void productsTest() throws Exception {
        this.mockMvc.perform(get("/cart/products").with(user(initUserDetailsImpl(true))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$.[0].id", is(1)))
                .andExpect(jsonPath("$.[0].productDto.imgUrl", is(PRODUCT_IMG_URL)))
                .andExpect(jsonPath("$.[0].productDto.name", is(PRODUCT_NAME_1)))
                .andExpect(jsonPath("$.[0].productDto.price", is(PRODUCT_PRICE_1.doubleValue())))
                .andExpect(jsonPath("$.[0].productDto.id", is(1)))
                .andExpect(jsonPath("$.[0].size", is("S")))
                .andExpect(jsonPath("$.[0].quantity", is(1)))
                .andExpect(jsonPath("$.[0].availableQuantity", is(2)))
                .andExpect(jsonPath("$.[1].id", is(2)))
                .andExpect(jsonPath("$.[1].productDto.imgUrl", is(PRODUCT_IMG_URL)))
                .andExpect(jsonPath("$.[1].productDto.name", is(PRODUCT_NAME_2)))
                .andExpect(jsonPath("$.[1].productDto.price", is(PRODUCT_PRICE_2.doubleValue())))
                .andExpect(jsonPath("$.[1].productDto.id", is(2)))
                .andExpect(jsonPath("$.[1].size", is("L")))
                .andExpect(jsonPath("$.[1].quantity", is(3)))
                .andExpect(jsonPath("$.[1].availableQuantity", is(5)));
    }

    @Test
    void addProductWhenHaveEnoughAvailableQuantity() throws Exception {
        initProducts();

        CartProductAddDto cartProductAddDto = new CartProductAddDto();
        cartProductAddDto.setProductId(1L);
        cartProductAddDto.setProductSize("S");

        this.mockMvc.perform(post("/cart/products")
                        .with(user(initUserDetailsImpl(false)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(cartProductAddDto)))
                .andExpect(status().isOk());
    }

    @Test
    void addProductWhenHaveEnoughAvailableQuantityAndCartProductAlreadyExist() throws Exception {
        initProducts();

        CartProductAddDto cartProductAddDto = new CartProductAddDto();
        cartProductAddDto.setProductId(1L);
        cartProductAddDto.setProductSize("S");

        MockHttpServletRequestBuilder mockRequest = post("/cart/products")
                .with(user(initUserDetailsImpl(false)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(cartProductAddDto));

        this.mockMvc.perform(mockRequest);

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isOk());
    }

    @Test
    void addProductWhenHaveNotEnoughAvailableQuantity() throws Exception {
        initProducts();

        CartProductAddDto cartProductAddDto = new CartProductAddDto();
        cartProductAddDto.setProductId(1L);
        cartProductAddDto.setProductSize("M");

        MockHttpServletRequestBuilder mockRequest = post("/cart/products")
                .with(user(initUserDetailsImpl(false)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(cartProductAddDto));

        this.mockMvc.perform(mockRequest);

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isNoContent());
    }

    @Test
    void updateQuantityTest() throws Exception {
        CartProductUpdateDto cartProductUpdateDto = new CartProductUpdateDto();
        cartProductUpdateDto.setQuantity(2);

        this.mockMvc.perform(patch("/cart/{productId}", 1)
                        .with(user(initUserDetailsImpl(true)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(cartProductUpdateDto)))
                .andExpect(status().isNoContent());
    }

    @Test
    void removeProductTest() throws Exception {
        this.mockMvc.perform(delete("/cart/{productId}", 1).with(user(initUserDetailsImpl(true))))
                .andExpect(status().isNoContent());
    }

    UserDetailsImpl initUserDetailsImpl(boolean withFullCart) {
        return UserDetailsImpl.build(initUserEntity(withFullCart));
    }

    UserEntity initUserEntity(boolean withFullCart) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(USER_ENTITY_FIRST_NAME);
        userEntity.setEmail(USER_ENTITY_EMAIL);
        userEntity.setPassword(this.passwordEncoder.encode(USER_ENTITY_PASSWORD));
        userEntity.setRole(ERole.USER);
        if (withFullCart) {
            userEntity.setCart(initCart());
        } else {
            userEntity.setCart(new Cart());
        }

        return this.userEntityRepository.save(userEntity);
    }

    Cart initCart() {
        initProducts();
        initCartProducts();

        Cart cart = new Cart();
        cart.setCartProducts(this.cartProductRepository.findAll());

        return cart;
    }

    void initProducts() {
        initCategory();

        Product productFirst = new Product();
        productFirst.setName(PRODUCT_NAME_1);
        productFirst.setPrice(PRODUCT_PRICE_1);
        productFirst.setCategory(this.categoryRepository.findByName(CATEGORY_NAME).get());
        productFirst = this.productRepository.save(productFirst);
        initQuantity(productFirst, 2, "S");
        initQuantity(productFirst, 1, "M");
        this.productRepository.save(productFirst);

        Product productSecond = new Product();
        productSecond.setName(PRODUCT_NAME_2);
        productSecond.setPrice(PRODUCT_PRICE_2);
        productSecond.setCategory(this.categoryRepository.findByName(CATEGORY_NAME).get());
        productSecond = this.productRepository.save(productSecond);
        initQuantity(productSecond, 5, "L");

        this.productRepository.save(productSecond);
    }

    void initCategory() {
        Category category = new Category();
        category.setName(CATEGORY_NAME);
        this.categoryRepository.save(category);
    }

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

    void initCartProducts() {
        CartProduct cartProductFirst = new CartProduct();
        cartProductFirst.setProduct(this.productRepository.findById(1L).get());
        cartProductFirst.setSize("S");
        cartProductFirst.setQuantity(1);
        this.cartProductRepository.save(cartProductFirst);

        CartProduct cartProductSecond = new CartProduct();
        cartProductSecond.setProduct(this.productRepository.findById(2L).get());
        cartProductSecond.setSize("L");
        cartProductSecond.setQuantity(3);
        this.cartProductRepository.save(cartProductSecond);
    }
}
