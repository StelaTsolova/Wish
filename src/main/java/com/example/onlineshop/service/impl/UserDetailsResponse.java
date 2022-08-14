package com.example.onlineshop.service.impl;

import java.util.List;

public class UserDetailsResponse {

    private String email;
    private String firstName;
    private List<String> roles;

    public UserDetailsResponse(String email, String firstName, List<String> roles) {
        this.email = email;
        this.firstName = firstName;
        this.roles = roles;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
