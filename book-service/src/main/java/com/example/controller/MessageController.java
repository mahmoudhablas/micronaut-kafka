package com.example.controller;

import com.example.model.Message;
import com.example.repository.MessageRepository;
import com.example.model.MessageUpdateCommand;
import io.micronaut.data.exceptions.DataAccessException;
import io.micronaut.data.model.Pageable;
import io.micronaut.http.HttpHeaders;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Status;
import io.micronaut.scheduling.TaskExecutors;
import io.micronaut.scheduling.annotation.ExecuteOn;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@ExecuteOn(TaskExecutors.IO)
@Controller("/messages")
public class MessageController {

    protected final com.example.repository.MessageRepository MessageRepository;

    public MessageController(MessageRepository MessageRepository) {
        this.MessageRepository = MessageRepository;
    }

    @Get("/{id}")
    public Optional<Message> show(Long id) {
        return MessageRepository
                .findById(id);
    }

    @Put
    public HttpResponse update(@Body @Valid MessageUpdateCommand command) {
        MessageRepository.update(command.getId(), command.getName());
        return HttpResponse
                .noContent()
                .header(HttpHeaders.LOCATION, location(command.getId()).getPath());
    }

    @Get("/list")
    public List<Message> list(@Valid Pageable pageable) {
        return MessageRepository.findAll(pageable).getContent();
    }

    @Post
    public HttpResponse<Message> save(@Body("name") @NotBlank String name) {
        Message Message = MessageRepository.save(name);

        return HttpResponse
                .created(Message)
                .headers(headers -> headers.location(location(Message.getId())));
    }

    @Post("/ex")
    public HttpResponse<Message> saveExceptions(@Body @NotBlank String name) {
        try {
            Message Message = MessageRepository.saveWithException(name);
            return HttpResponse
                    .created(Message)
                    .headers(headers -> headers.location(location(Message.getId())));
        } catch(DataAccessException e) {
            return HttpResponse.noContent();
        }
    }

    @Delete("/{id}")
    @Status(HttpStatus.NO_CONTENT)
    public void delete(Long id) {
        MessageRepository.deleteById(id);
    }

    protected URI location(Long id) {
        return URI.create("/messages/" + id);
    }

    protected URI location(Message Message) {
        return location(Message.getId());
    }
}
