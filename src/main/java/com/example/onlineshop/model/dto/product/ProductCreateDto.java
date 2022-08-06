package com.example.onlineshop.model.dto.product;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Map;

public class ProductCreateDto {

    @NotBlank
    @Size(max = 20, message = "Name should be less than 20 symbols.")
    private String name;

    @NotBlank
    private BigDecimal price;

    @NotBlank
    private String categoryName;

    private String material;

    @NotBlank
    private Map<String , Integer> quantities;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public Map<String, Integer> getQuantities() {
        return quantities;
    }

    public void setQuantities(Map<String, Integer> quantities) {
        this.quantities = quantities;
    }
}
