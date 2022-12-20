package com.example.controller;
import com.example.service.AnalyticsService;
import com.example.model.BookAnalytics;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.List;

@Controller("/analytics")
class AnalyticsController {

    private final AnalyticsService analyticsService;

    AnalyticsController(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @Get
    List<BookAnalytics> listAnalytics() {
        return analyticsService.listAnalytics();
    }
}