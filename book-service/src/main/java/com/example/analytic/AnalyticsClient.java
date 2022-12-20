package com.example.analytic;

import com.example.model.Book;
import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;
import reactor.core.publisher.Mono;

@KafkaClient
public interface AnalyticsClient {

    @Topic("analytics")
    Mono<Book> updateAnalytics(Book book);

    @Topic("analytics")
    void updateAnalytics2(Book book);
}