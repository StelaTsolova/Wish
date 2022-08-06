package com.example.onlineshop.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "carts")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<CartProduct> cartProducts;

    @OneToOne(mappedBy = "cart")
    private UserEntity userEntity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public void setCartProducts(List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public BigDecimal getTotalPrice() {
        return this.cartProducts.stream()
                .map(p -> p.getProduct().getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void emptyingCart(){
        this.cartProducts.clear();
    }
}
