package com.example.onlineshop.repisotiry;

import com.example.onlineshop.model.entity.StatsOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatsOrderRepository extends JpaRepository<StatsOrder, Long> {
}
