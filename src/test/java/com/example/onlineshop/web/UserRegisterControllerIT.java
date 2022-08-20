package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.user.UserRegisterDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserRegisterControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void registerWithValidUserRegisterDto() throws Exception {
        UserRegisterDto userRegisterDto = initUserRegisterDto("valid@gmail.com", "88888888", "Misho");

        MockHttpServletRequestBuilder mockRequest = post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userRegisterDto));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("User registered successfully!")));
    }

    @Test
    void registerWithNotValidUserRegisterDto() throws Exception {
        UserRegisterDto userRegisterDto = initUserRegisterDto("notValidEmail", "888", "Misho");
        MockHttpServletRequestBuilder mockRequest = post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(userRegisterDto));

        this.mockMvc.perform(mockRequest)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.email", is("Incorrect email.")))
                .andExpect(jsonPath("$.password", is("Password should be between 8 and 40 symbols.")));
    }

    UserRegisterDto initUserRegisterDto(String email, String password, String firstName){
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setEmail(email);
        userRegisterDto.setPassword(password);
        userRegisterDto.setFirstName(firstName);

        return userRegisterDto;
    }
}
