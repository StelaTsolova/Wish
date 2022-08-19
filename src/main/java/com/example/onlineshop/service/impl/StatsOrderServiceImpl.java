package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.entity.StatsOrder;
import com.example.onlineshop.repisotiry.StatsOrderRepository;
import com.example.onlineshop.service.StatsOrderService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class StatsOrderServiceImpl implements StatsOrderService {

    private int count;
    private final StatsOrderRepository statsOrderRepository;

    public StatsOrderServiceImpl(StatsOrderRepository statsOrderRepository) {
        this.statsOrderRepository = statsOrderRepository;
    }

    @Override
    public void increaseCount() {
        this.count++;
    }

    @Override
    public void saveStats() {
        StatsOrder statsOrder = new StatsOrder(LocalDate.now(), this.count);
        this.statsOrderRepository.save(statsOrder);

        this.count = 0;
    }
}
