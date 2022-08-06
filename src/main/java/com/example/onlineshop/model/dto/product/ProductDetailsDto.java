package com.example.onlineshop.model.dto.product;

import com.example.onlineshop.model.dto.QuantityDto;
import com.example.onlineshop.model.entity.Quantity;
import com.example.onlineshop.model.entity.Size;

import java.math.BigDecimal;
import java.util.List;

public class ProductDetailsDto {

    private List<String> imgUrls;
    private String name;
    private BigDecimal price;
    private String material;
    private List<QuantityDto> quantities;

    public List<String> getImgUrls() {
        return imgUrls;
    }

    public void setImgUrls(List<String> imgUrls) {
        this.imgUrls = imgUrls;
    }

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

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public List<QuantityDto> getQuantities() {
        return quantities;
    }

    public void setQuantities(List<QuantityDto> quantities) {
        this.quantities = quantities;
    }
}
