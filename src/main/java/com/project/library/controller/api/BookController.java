package com.project.library.controller.api;

import com.project.library.controller.CreateBookRequest;
import com.project.library.domain.Book;
import com.project.library.service.BookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping("/api/v1/books")
    public Book createBook(@RequestBody CreateBookRequest bookRequest) {
        return bookService.createBook(
                bookRequest.title(),
                bookRequest.category(),
                bookRequest.price(),
                bookRequest.author(),
                bookRequest.isbn()
        );
    }
}
