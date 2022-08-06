package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.entity.Product;
import com.example.onlineshop.model.entity.Quantity;
import com.example.onlineshop.model.entity.Size;
import com.example.onlineshop.repisotiry.QuantityRepository;
import com.example.onlineshop.service.QuantityService;
import com.example.onlineshop.service.SizeService;
import com.example.onlineshop.web.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuantityServiceImpl implements QuantityService {

    private final QuantityRepository quantityRepository;
    private final SizeService sizeService;

    public QuantityServiceImpl(QuantityRepository quantityRepository, SizeService sizeService) {
        this.quantityRepository = quantityRepository;
        this.sizeService = sizeService;
    }

    @Override
    public List<Quantity> getQuantitiesByProductId(Long productId) {
        return this.quantityRepository.findByProduct_Id(productId);
    }

    @Override
    public int getAvailableQuantityByProductIdAndSizeName(Long productId, String sizeName) {
        return this.quantityRepository.findByProduct_IdAndSize_Name(productId, sizeName)
                .orElseThrow(() -> new ObjectNotFoundException("Quantity with productId " + productId + " and sizeName " + sizeName + " is not found!"))
                .getAvailableQuantity();
    }

    @Override
    public List<Quantity> saveQuantities(Map<String, Integer> quantities, Product product) {
        return quantities.entrySet().stream().map(entry -> {
                    Size size = this.sizeService.getSizeByName(entry.getKey());
                    Quantity quantity = new Quantity();
                    quantity.setProduct(product);
                    quantity.setSize(size);
                    quantity.setAvailableQuantity(entry.getValue());

                    return this.quantityRepository.save(quantity);
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Quantity> updateQuantities(Map<String, Integer> quantities, Product product) {
       this.quantityRepository.deleteAll(this.getQuantitiesByProductId(product.getId()));

        return saveQuantities(quantities, product);
    }
}
