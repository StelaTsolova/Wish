package com.example.onlineshop.service;

import com.example.onlineshop.model.dto.StatisticDto;

public interface StatisticService {

    void onRequest();

    StatisticDto getStatistic();
}
