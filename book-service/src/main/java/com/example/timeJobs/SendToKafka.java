package com.example.timeJobs;

import com.example.analytic.AnalyticsClient;
import com.example.model.Book;
import com.example.model.Message;
import com.example.repository.MessageRepository;
import io.micronaut.scheduling.annotation.Scheduled;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Singleton;
import reactor.core.publisher.Flux;

import java.text.SimpleDateFormat;
import java.util.Date;

@Singleton
public class SendToKafka {
    private static final Logger LOG = LoggerFactory.getLogger(SendToKafka.class);
    protected final com.example.repository.MessageRepository MessageRepository;
    private final AnalyticsClient analyticsClient;

    public SendToKafka(MessageRepository MessageRepository, AnalyticsClient analyticsClient){
        this.MessageRepository = MessageRepository;
        this.analyticsClient = analyticsClient;
    }
    @Scheduled(fixedDelay = "10s")
    void executeEveryTen() {
        // select from message
        Iterable<Message> ms = MessageRepository.findAll();
        ms.forEach(
                (message)-> {
                    System.out.println("Sending the :" + message);
                    Book book = new Book(message.getId().toString(),message.getName());
                    analyticsClient.updateAnalytics2(book);

                }

        );
        LOG.info("Simple Job every 10 seconds: {}", new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
    }

    @Scheduled(fixedDelay = "45s", initialDelay = "5s")
    void executeEveryFourtyFive() {
        LOG.info("Simple Job every 45 seconds: {}", new SimpleDateFormat("dd/M/yyyy hh:mm:ss").format(new Date()));
    }
}