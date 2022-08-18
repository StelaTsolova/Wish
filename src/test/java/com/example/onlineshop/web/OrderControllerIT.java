package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.OrderCreateDto;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.math.BigDecimal;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerIT {

    private static final String USER_ENTITY_FIRST_NAME = "Pesho";
    private static final String USER_ENTITY_LAST_NAME = "Peshov";
    private static final String USER_ENTITY_EMAIL = "pesho@gmail.com";
    private static final String USER_ENTITY_PASSWORD_1 = "12345678";
    private static final String USER_ENTITY_PHONE_NUMBER = "9999999999";
    private static final String CATEGORY_NAME_1 = "categoryFirst";
    private static final String PRODUCT_IMG_URL = "https://res.cloudinary.com/dj0dxejrk/image/upload/v1660579108/wish_c5gw2r.png";
    private static final String PRODUCT_NAME_1 = "nameFirst";
    private static final String PRODUCT_NAME_2 = "nameSecond";
    private static final BigDecimal PRODUCT_PRICE_1 = BigDecimal.valueOf(1);
    private static final BigDecimal PRODUCT_PRICE_2 = BigDecimal.valueOf(2);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderRepository orderRepository;

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
    void createOrderWithValidOrderCreateDto() throws Exception {
        OrderCreateDto orderCreateDto = initOrderCreateDto(USER_ENTITY_FIRST_NAME, USER_ENTITY_LAST_NAME,
                USER_ENTITY_EMAIL, USER_ENTITY_PHONE_NUMBER, "Town", "address");

        this.mockMvc.perform(post("/orders")
                .with(user(initUserDetailsImpl(false)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(orderCreateDto)))
                .andExpect(status().isOk());
    }

    @Test
    void createOrderWithNotValidOrderCreateDto() throws Exception {
        OrderCreateDto orderCreateDto = initOrderCreateDto("", " ", "email",
                "999", "   ", "");

        MockHttpServletRequestBuilder mockRequest = post("/orders")
                .with(user(initUserDetailsImpl(false)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(orderCreateDto));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.firstName", is("must not be blank")))
                .andExpect(jsonPath("$.lastName", is("must not be blank")))
                .andExpect(jsonPath("$.email", is("Incorrect email.")))
                .andExpect(jsonPath("$.phoneNumber", is("Phone number should be between 10 and 20 symbols.")))
                .andExpect(jsonPath("$.town", is("must not be blank")))
                .andExpect(jsonPath("$.address", is("must not be blank")));
    }

    UserDetailsImpl initUserDetailsImpl(boolean withFullCart) {
        return UserDetailsImpl.build(initUserEntity(withFullCart));
    }

    UserEntity initUserEntity(boolean withFullCart) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(USER_ENTITY_FIRST_NAME);
        userEntity.setLastName(USER_ENTITY_LAST_NAME);
        userEntity.setPhoneNumber(USER_ENTITY_PHONE_NUMBER);
        userEntity.setEmail(USER_ENTITY_EMAIL);
        userEntity.setPassword(this.passwordEncoder.encode(USER_ENTITY_PASSWORD_1));
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
        productFirst.setCategory(this.categoryRepository.findByName(CATEGORY_NAME_1).get());
        productFirst = this.productRepository.save(productFirst);
        initQuantity(productFirst, 2, "S");
        initQuantity(productFirst, 1, "M");
        this.productRepository.save(productFirst);

        Product productSecond = new Product();
        productSecond.setName(PRODUCT_NAME_2);
        productSecond.setPrice(PRODUCT_PRICE_2);
        productSecond.setCategory(this.categoryRepository.findByName(CATEGORY_NAME_1).get());
        productSecond = this.productRepository.save(productSecond);
        initQuantity(productSecond, 5, "L");

        this.productRepository.save(productSecond);
    }

    void initCategory() {
        Category category = new Category();
        category.setName(CATEGORY_NAME_1);
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

    OrderCreateDto initOrderCreateDto(String firstName, String lastName, String email, String phoneNumber, String town, String address){
        OrderCreateDto orderCreateDto = new OrderCreateDto();
        orderCreateDto.setFirstName(firstName);
        orderCreateDto.setLastName(lastName);
        orderCreateDto.setEmail(email);
        orderCreateDto.setPhoneNumber(phoneNumber);
        orderCreateDto.setTown(town);
        orderCreateDto.setAddress(address);

        return orderCreateDto;
    }
}
