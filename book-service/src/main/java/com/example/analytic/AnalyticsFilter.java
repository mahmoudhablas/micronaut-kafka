package com.example.analytic;

import com.example.repository.MessageRepository;
import com.example.model.Book;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.Filter;
import io.micronaut.http.filter.HttpServerFilter;
import io.micronaut.http.filter.ServerFilterChain;
import reactor.core.publisher.Flux;
import org.reactivestreams.Publisher;

@Filter("/books/?*")
public class AnalyticsFilter implements HttpServerFilter {

    protected final com.example.repository.MessageRepository MessageRepository;

    private final AnalyticsClient analyticsClient;

    public AnalyticsFilter(AnalyticsClient analyticsClient,MessageRepository MessageRepository ) {
        this.analyticsClient = analyticsClient;
        this.MessageRepository = MessageRepository;
    }

    @Override
    public Publisher<MutableHttpResponse<?>> doFilter(HttpRequest<?> request,
                                                      ServerFilterChain chain) {
        return Flux
                .from(chain.proceed(request))
                .flatMap(response -> {
                    Book book = response.getBody(Book.class).orElse(null);
                    if (book == null) {
                        return Flux.just(response);
                    }
                    return Flux.from(analyticsClient.updateAnalytics(book)).map(b -> response).doOnError(
                            (err) -> {

                                //insert into DB to be picked after Kafka is up
                               MessageRepository.save("hello from error");
                            }
                    );
                });
    }
}