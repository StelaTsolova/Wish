package com.example.onlineshop.service;

import com.example.onlineshop.model.entity.Product;
import com.example.onlineshop.model.entity.Quantity;

import java.util.List;
import java.util.Map;

public interface QuantityService {

    List<Quantity> getQuantitiesByProductId(Long productId);

    int getAvailableQuantityByProductIdAndSizeName(Long productId, String sizeName);

    List<Quantity> saveQuantities(Map<String, Integer> quantities, Product product);

    List<Quantity> updateQuantities(Map<String, Integer> quantities, Product product);
}
