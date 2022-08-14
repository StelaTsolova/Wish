package com.example.onlineshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlController {

    @GetMapping("/home")
    public String home() {
        return "index.html";
    }

    @GetMapping("/navbar")
    public String navbar() {
        return "navbar.html";
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/register")
    public String register() {
        return "register.html";
    }

    @GetMapping("/information")
    public String information() {
        return "account.html";
    }

    @GetMapping("/change")
    public String change() {
        return "changePassword.html";
    }

    @GetMapping("/wishlist")
    public String wishlist() {
        return "wishlist.html";
    }

    @GetMapping("/cart")
    public String cart() {
        return "cart.html";
    }

    @GetMapping("/products")
    public String products() {
        return "products.html";
    }

    @GetMapping("/details")
    public String details() {
        return "details.html";
    }

    @GetMapping("/product")
    public String createProduct() {
        return "createProduct.html";
    }

    @GetMapping("/user")
    public String createUser() {
        return "createUser.html";
    }

    @GetMapping("/data")
    public String data() {
        return "data.html";
    }

    @GetMapping("/order")
    public String order() {
        return "order.html";
    }

    @GetMapping("/statistic")
    public String statistic() {
        return "statistic.html";
    }
}
