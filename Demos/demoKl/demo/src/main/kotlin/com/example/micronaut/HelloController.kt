package com.example.micronaut

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get

@Controller
internal class HelloController {
    @Get("/hello")
    fun sayHello(): String {
        return "Hello World from Micronaut"
    }
}