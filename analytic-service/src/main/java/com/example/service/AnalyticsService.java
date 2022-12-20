package com.example.service;

import com.example.model.Book;
import com.example.model.BookAnalytics;
import jakarta.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Singleton
public class AnalyticsService {

    private final Map<Book, Long> bookAnalytics = new ConcurrentHashMap<>();

    public void updateBookAnalytics(Book book) {
        bookAnalytics.compute(book, (k, v) -> {
            return v == null ? 1L : v + 1;
        });
    }

    public List<BookAnalytics> listAnalytics() {
        return bookAnalytics
                .entrySet()
                .stream()
                .map(e -> new BookAnalytics(e.getKey().getIsbn(), e.getValue()))
                .collect(Collectors.toList());
    }
}