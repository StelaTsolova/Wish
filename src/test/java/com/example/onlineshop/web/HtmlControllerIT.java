package com.example.onlineshop.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.io.File;
import java.nio.file.Files;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HtmlControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void homeTest() throws Exception {
        this.mockMvc.perform(get("/home"))
                .andExpect(view().name("index.html"));
    }

    @Test
    void navbarTest() throws Exception {
        this.mockMvc.perform(get("/navbar"))
                .andExpect(view().name("navbar.html"));
    }

    @Test
    void loginTest() throws Exception {
        this.mockMvc.perform(get("/login"))
                .andExpect(view().name("login.html"));
    }

    @Test
    void registerTest() throws Exception {
        this.mockMvc.perform(get("/register"))
                .andExpect(view().name("register.html"));
    }

    @Test
    @WithMockUser()
    void informationTest() throws Exception {
        this.mockMvc.perform(get("/information"))
                .andExpect(view().name("account.html"));
    }

    @Test
    @WithMockUser()
    void changeTest() throws Exception {
        this.mockMvc.perform(get("/change"))
                .andExpect(view().name("changePassword.html"));
    }

    @Test
    @WithMockUser()
    void wishlistTest() throws Exception {
        this.mockMvc.perform(get("/wishlist"))
                .andExpect(view().name("wishlist.html"));
    }

    @Test
    @WithMockUser()
    void cartTest() throws Exception {
        this.mockMvc.perform(get("/cart"))
                .andExpect(view().name("cart.html"));
    }

    @Test
    void productsTest() throws Exception {
        this.mockMvc.perform(get("/products"))
                .andExpect(view().name("products.html"));
    }

    @Test
    void detailsTest() throws Exception {
        this.mockMvc.perform(get("/details"))
                .andExpect(view().name("details.html"));
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void createProductTest() throws Exception {
        this.mockMvc.perform(get("/product"))
                .andExpect(view().name("createProduct.html"));
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void createUserTest() throws Exception {
        this.mockMvc.perform(get("/user"))
                .andExpect(view().name("createUser.html"));
    }

    @Test
    @WithMockUser
    void dataTest() throws Exception {
        this.mockMvc.perform(get("/data"))
                .andExpect(view().name("data.html"));
    }

    @Test
    @WithMockUser
    void orderTest() throws Exception {
        this.mockMvc.perform(get("/order"))
                .andExpect(view().name("order.html"));
    }

    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    void statisticTest() throws Exception {
        this.mockMvc.perform(get("/statistic"))
                .andExpect(view().name("statistic.html"));
    }
}
