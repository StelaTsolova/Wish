package com.example.onlineshop.model.dto;

public class StatisticDto {

    private final int authRequests;
    private final int anonymousRequests;

    public StatisticDto(int authRequests, int anonymousRequests) {
        this.authRequests = authRequests;
        this.anonymousRequests = anonymousRequests;
    }

    public int getAuthRequests() {
        return authRequests;
    }

    public int getAnonymousRequests() {
        return anonymousRequests;
    }

    public int getTotalRequests(){
        return this.authRequests + this.anonymousRequests;
    }
}
