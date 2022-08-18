package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.WishlistAddDto;
import com.example.onlineshop.model.dto.user.UserChangePasswordDto;
import com.example.onlineshop.model.dto.user.UserCreateDto;
import com.example.onlineshop.model.dto.user.UserUpdateInformationDto;
import com.example.onlineshop.model.entity.Category;
import com.example.onlineshop.model.entity.Product;
import com.example.onlineshop.model.entity.UserEntity;
import com.example.onlineshop.model.enums.ERole;
import com.example.onlineshop.repisotiry.CategoryRepository;
import com.example.onlineshop.repisotiry.ProductRepository;
import com.example.onlineshop.repisotiry.UserEntityRepository;
import com.example.onlineshop.service.impl.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserEntityControllerIT {

    private static final String USER_ENTITY_FIRST_NAME = "Pesho";
    private static final String USER_ENTITY_LAST_NAME = "Peshov";
    private static final String USER_ENTITY_PHONE_NUMBER = "9999999999";
    private static final String USER_ENTITY_EMAIL = "pesho@gmail.com";
    private static final String USER_ENTITY_PASSWORD = "12345678";
    private static final String CATEGORY_NAME_1 = "categoryFirst";
    private static final String CATEGORY_NAME_2 = "categorySecond";
    private static final String PRODUCT_NAME_1 = "nameFirst";
    private static final String PRODUCT_NAME_2 = "nameSecond";
    private static final String PRODUCT_NAME_3 = "nameThird";
    private static final BigDecimal PRODUCT_PRICE_1 = BigDecimal.valueOf(1);
    private static final BigDecimal PRODUCT_PRICE_2 = BigDecimal.valueOf(2);
    private static final BigDecimal PRODUCT_PRICE_3 = BigDecimal.valueOf(3);
    private static final String PRODUCT_MATERIAL_1 = "materialFirst";
    private static final String PRODUCT_IMG_URL = "https://res.cloudinary.com/dj0dxejrk/image/upload/v1660579108/wish_c5gw2r.png";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserEntityRepository userEntityRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test()
    void getUserTest() throws Exception {
        this.mockMvc.perform(get("/users").with(user(initUserDetailsImpl(false))))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.firstName", is(USER_ENTITY_FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", is(USER_ENTITY_LAST_NAME)))
                .andExpect(jsonPath("$.phoneNumber", is(USER_ENTITY_PHONE_NUMBER)));
    }

    @Test
    void productsTest() throws Exception {
        this.mockMvc.perform(get("/users/wishlist").with(user(initUserDetailsImpl(true))))
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$.[0].imgUrl", is(PRODUCT_IMG_URL)))
                .andExpect(jsonPath("$.[0].name", is(PRODUCT_NAME_1)))
                .andExpect(jsonPath("$.[0].price", is(PRODUCT_PRICE_1.doubleValue())))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$.[1].imgUrl", is(PRODUCT_IMG_URL)))
                .andExpect(jsonPath("$.[1].name", is(PRODUCT_NAME_2)))
                .andExpect(jsonPath("$.[1].price", is(PRODUCT_PRICE_2.doubleValue())))
                .andExpect(jsonPath("$[2].id", is(3)))
                .andExpect(jsonPath("$.[2].imgUrl", is(PRODUCT_IMG_URL)))
                .andExpect(jsonPath("$.[2].name", is(PRODUCT_NAME_3)))
                .andExpect(jsonPath("$.[2].price", is(PRODUCT_PRICE_3.doubleValue())));
    }

    @Test
    void addProductTest() throws Exception {
        WishlistAddDto wishlistAddDto = new WishlistAddDto();
        wishlistAddDto.setProductId(1L);

        this.mockMvc.perform(post("/users/wishlist")
                        .with(user(initUserDetailsImpl(true)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(wishlistAddDto)))
                .andExpect(status().isOk());
    }

    @Test
    void updateInformationWithValidUserUpdateInformationDto() throws Exception {
        UserUpdateInformationDto userUpdateInformationDto = new UserUpdateInformationDto();
        userUpdateInformationDto.setFirstName("Gosho");

        MockHttpServletRequestBuilder mockRequest = put("/users")
                .with(user(initUserDetailsImpl(false)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userUpdateInformationDto));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(userUpdateInformationDto.getFirstName())));
    }

    @Test
    void updateInformationWithNotValidUserUpdateInformationDto() throws Exception {
        UserUpdateInformationDto userUpdateInformationDto = new UserUpdateInformationDto();

        MockHttpServletRequestBuilder mockRequest = put("/users")
                .with(user(initUserDetailsImpl(false)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userUpdateInformationDto));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName", is("must not be blank")));
    }

    @Test
    void changePasswordWithValidUserChangePasswordDto() throws Exception {
        UserChangePasswordDto userChangePasswordDto = new UserChangePasswordDto();
        userChangePasswordDto.setPassword(USER_ENTITY_PASSWORD);
        userChangePasswordDto.setNewPassword("87654321");

        this.mockMvc.perform(put("/users/change").with(user(initUserDetailsImpl(false)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(userChangePasswordDto)))
                .andExpect(status().isOk());
    }

    @Test
    void changePasswordWithNotValidNewPasswordInUserChangePasswordDto() throws Exception {
        UserChangePasswordDto userChangePasswordDto = new UserChangePasswordDto();
        userChangePasswordDto.setPassword(USER_ENTITY_PASSWORD);
        userChangePasswordDto.setNewPassword("wrong");

        MockHttpServletRequestBuilder mockRequest = put("/users/change")
                .with(user(initUserDetailsImpl(false)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userChangePasswordDto));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.massage", is("New password should be between 8 and 40 symbols.")));
    }

    @Test
    void changePasswordWithNotValidPasswordInUserChangePasswordDto() throws Exception {
        UserChangePasswordDto userChangePasswordDto = new UserChangePasswordDto();
        userChangePasswordDto.setPassword("wrongPassword");
        userChangePasswordDto.setNewPassword("87654321");

        MockHttpServletRequestBuilder mockRequest = put("/users/change")
                .with(user(initUserDetailsImpl(false)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userChangePasswordDto));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.massage", is("Wrong password.")));
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void createUserWithValidUserCreateDto() throws Exception {
        UserCreateDto userCreateDto = initUserCreateDto("email@gmail.com", "password", "Name", "USER");

        this.mockMvc.perform(post("/users").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapper.writeValueAsString(userCreateDto)))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void createUserWithNotValidUserCreateDto() throws Exception {
        UserCreateDto userCreateDto = initUserCreateDto("e", "pass", " ", "");

        MockHttpServletRequestBuilder mockRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userCreateDto));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is("Email should be more than 3 symbols.")))
                .andExpect(jsonPath("$.password", is("Password should be between 8 and 40 symbols.")))
                .andExpect(jsonPath("$.firstName", is("must not be blank")))
                .andExpect(jsonPath("$.role", is("must not be blank")));
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void createUserWithNotValidUserCreateDtoWhenEmailNotUnique() throws Exception {
        initUserEntity(false);
        UserCreateDto userCreateDto = initUserCreateDto(USER_ENTITY_EMAIL, "password", "Name", "USER");

        MockHttpServletRequestBuilder mockRequest = post("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userCreateDto));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is("User with this email already exist.")));
    }

    UserDetailsImpl initUserDetailsImpl(boolean withWishlist) {
        return UserDetailsImpl.build(initUserEntity(withWishlist));
    }

    UserEntity initUserEntity(boolean withWishlist) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(USER_ENTITY_FIRST_NAME);
        userEntity.setLastName(USER_ENTITY_LAST_NAME);
        userEntity.setPhoneNumber(USER_ENTITY_PHONE_NUMBER);
        userEntity.setEmail(USER_ENTITY_EMAIL);
        userEntity.setPassword(this.passwordEncoder.encode(USER_ENTITY_PASSWORD));
        userEntity.setRole(ERole.USER);

        if (withWishlist) {
            initProducts();
            userEntity.setWishlist(this.productRepository.findAll());
        }

        return this.userEntityRepository.save(userEntity);
    }

    void initProducts() {
        initCategories();

        Product productFirst = new Product();
        productFirst.setName(PRODUCT_NAME_1);
        productFirst.setPrice(PRODUCT_PRICE_1);
        productFirst.setMaterial(PRODUCT_MATERIAL_1);
        productFirst.setCategory(this.categoryRepository.findByName(CATEGORY_NAME_1).get());
        productFirst = this.productRepository.save(productFirst);
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

    UserCreateDto initUserCreateDto(String email, String password, String firstName, String role) {
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setEmail(email);
        userCreateDto.setPassword(password);
        userCreateDto.setFirstName(firstName);
        userCreateDto.setRole(role);

        return userCreateDto;
    }
}
