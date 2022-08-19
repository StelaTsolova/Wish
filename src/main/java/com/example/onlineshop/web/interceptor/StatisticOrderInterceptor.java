package com.example.onlineshop.web.interceptor;

import com.example.onlineshop.service.StatsOrderService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class StatisticOrderInterceptor implements HandlerInterceptor {

    private final StatsOrderService statsOrderService;

    public StatisticOrderInterceptor(StatsOrderService statsOrderService) {
        this.statsOrderService = statsOrderService;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        if(request.getRequestURI().equals("/orders") && response.getStatus() == 200){
            this.statsOrderService.increaseCount();
        }
    }
}
