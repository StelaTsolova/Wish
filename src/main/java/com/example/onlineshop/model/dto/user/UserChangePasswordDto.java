package com.example.onlineshop.model.dto.user;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserChangePasswordDto {

    @NotBlank
    private String password;

    @Size(min = 8, max = 40, message = "New password should be between 8 and 40 symbols.")
    @NotBlank
    private String newPassword;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
