package com.example.controller;

import io.micronaut.context.annotation.Property;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;


@Controller
public class HelloController {

    @Get("/hello")
    public String sayHello()
    {
        return "Hello World";
    }

}
