package com.example.onlineshop.repisotiry;

import com.example.onlineshop.model.entity.Quantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface QuantityRepository extends JpaRepository<Quantity, Long> {

    List<Quantity> findByProduct_Id(Long productId);

     Optional<Quantity> findByProduct_IdAndSize_Name(Long productId, String sizeName);
}
