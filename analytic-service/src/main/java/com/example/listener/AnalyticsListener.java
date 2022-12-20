package com.example.listener;

import com.example.model.Book;
import com.example.service.AnalyticsService;
import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.context.annotation.Requires;
import io.micronaut.context.env.Environment;

@Requires(notEnv = Environment.TEST)
@KafkaListener
public class AnalyticsListener {

    private final AnalyticsService analyticsService;

    public AnalyticsListener(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @Topic("analytics")
    public void updateAnalytics(Book book) {
        analyticsService.updateBookAnalytics(book);
    }
}