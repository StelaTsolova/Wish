package com.example.onlineshop.web.interceptor;

import com.example.onlineshop.service.StatisticService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class StatisticInterceptor implements HandlerInterceptor {

    private final StatisticService statsService;;

    public StatisticInterceptor(StatisticService statsService) {
        this.statsService = statsService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
       this.statsService.onRequest();

        return true;
    }
}
