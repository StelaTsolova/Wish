package com.example.onlineshop.config;

import com.example.onlineshop.web.interceptor.StatisticInterceptor;
import com.example.onlineshop.web.interceptor.StatisticOrderInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    private final StatisticInterceptor statisticInterceptor;
    private final StatisticOrderInterceptor statisticOrderInterceptor;

    public WebConfiguration(StatisticInterceptor statsInterceptor1, StatisticOrderInterceptor statisticOrderInterceptor) {
        this.statisticInterceptor = statsInterceptor1;
        this.statisticOrderInterceptor = statisticOrderInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.statisticInterceptor);
        registry.addInterceptor(this.statisticOrderInterceptor);
    }
}
