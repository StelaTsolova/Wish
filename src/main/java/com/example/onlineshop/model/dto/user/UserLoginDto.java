package com.example.onlineshop.model.dto.user;

import org.springframework.context.annotation.Bean;

import javax.swing.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserLoginDto {

    @NotBlank
    @Size(min = 3)
    private String email;

    @NotBlank
    @Size(min = 8, max = 40)
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
