package com.example.controller;
import com.example.model.Book;
import com.example.service.BookService;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.List;
import java.util.Optional;

@Controller("/books")
class BookController {

    private final BookService bookService;

    BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Get
    List<Book> listAll() {
        return bookService.listAll();
    }

    @Get("/{isbn}")
    Optional<Book> findBook(String isbn) {
        return bookService.findByIsbn(isbn);
    }
}