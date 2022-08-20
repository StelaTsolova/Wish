package com.example.onlineshop.web;

import com.example.onlineshop.model.dto.StatisticDto;
import com.example.onlineshop.service.StatisticService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatisticController {

    private final StatisticService statisticService;

    public StatisticController(StatisticService statsService) {
        this.statisticService = statsService;
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<StatisticDto> getStatistic(){
        return ResponseEntity.ok(this.statisticService.getStatistic());
    }
}