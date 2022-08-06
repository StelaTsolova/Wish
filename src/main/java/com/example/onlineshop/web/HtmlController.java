package com.example.onlineshop.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HtmlController {

    @RequestMapping("/home")
    public String home() {
        return "index.html";
    }

    @RequestMapping("/navbar")
    public String navbar() {
        return "navbar.html";
    }

    @RequestMapping("/login")
    public String login() {
        return "login.html";
    }

    @RequestMapping("/register")
    public String register() {
        return "register.html";
    }

    @RequestMapping("/information")
    public String information() {
        return "account.html";
    }

    @RequestMapping("/change")
    public String change() {
        return "changePassword.html";
    }

    @RequestMapping("/wishlist")
    public String wishlist() {
        return "wishlist.html";
    }

    @RequestMapping("/cart")
    public String cart() {
        return "cart.html";
    }

    @RequestMapping("/products")
    public String products() {
        return "products.html";
    }

    @RequestMapping("/details")
    public String details() {
        return "details.html";
    }

    @RequestMapping("/product")
    public String createProduct() {
        return "createProduct.html";
    }

    @RequestMapping("/user")
    public String createUser() {
        return "createUser.html";
    }

    @RequestMapping("/data")
    public String data() {
        return "data.html";
    }

    @RequestMapping("/order")
    public String order() {
        return "order.html";
    }

    @RequestMapping("/statistic")
    public String statistic() {
        return "statistic.html";
    }
}
