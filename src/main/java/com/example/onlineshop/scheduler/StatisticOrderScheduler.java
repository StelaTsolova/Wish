package com.example.onlineshop.scheduler;

import com.example.onlineshop.service.StatsOrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class StatisticOrderScheduler {

    private final StatsOrderService statsOrderService;

    public StatisticOrderScheduler(StatsOrderService statsOrderService) {
        this.statsOrderService = statsOrderService;
    }

    @Scheduled(cron = "0 59 23 * * *")
    public void createStatsOrder() {
        this.statsOrderService.saveStats();
    }
}
