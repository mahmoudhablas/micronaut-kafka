package com.example.model;
import io.micronaut.serde.annotation.Serdeable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Serdeable
public class MessageUpdateCommand {
    @NotNull
    private final Long id;

    @NotBlank
    private final String name;

    public MessageUpdateCommand(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}