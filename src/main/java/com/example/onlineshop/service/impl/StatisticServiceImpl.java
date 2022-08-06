package com.example.onlineshop.service.impl;

import com.example.onlineshop.model.dto.StatisticDto;
import com.example.onlineshop.service.StatisticService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class StatisticServiceImpl implements StatisticService {

    private int authRequests;
    private int anonymousRequests;

    @Override
    public void onRequest() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && (authentication.getPrincipal() instanceof UserDetails)) {
            this.authRequests++;
        } else {
            this.anonymousRequests++;
        }
    }

    @Override
    public StatisticDto getStatistic() {
        return new StatisticDto(authRequests, anonymousRequests);
    }
}
