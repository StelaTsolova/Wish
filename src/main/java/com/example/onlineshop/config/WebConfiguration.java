package com.example.onlineshop.config;

import com.example.onlineshop.web.interceptor.StatisticInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final StatisticInterceptor statisticInterceptor;

    public WebConfiguration(StatisticInterceptor statsInterceptor1) {
        this.statisticInterceptor = statsInterceptor1;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.statisticInterceptor);
    }
}
