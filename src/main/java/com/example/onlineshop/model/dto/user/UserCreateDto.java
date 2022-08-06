package com.example.onlineshop.model.dto.user;

import com.example.onlineshop.model.validator.UserEmailUnique;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserCreateDto {

    @NotBlank()
    @Size(min = 3, message = "Email should be more than 3 symbols.")
    @UserEmailUnique
    private String email;

    @Size(min = 8, max = 40,  message = "Password should be between 8 and 40 symbols.")
    private String password;

    @NotBlank
    private String firstName;

    private String lastName;

    @NotBlank
    private String role;

    private String phoneNumber;

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
