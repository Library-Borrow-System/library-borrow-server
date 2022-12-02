package com.project.library.controller.api;

import com.project.library.controller.CreateBookRequest;
import com.project.library.domain.Book;
import com.project.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

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

    @GetMapping("/api/v1/books")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/api/v1/book/{bookId}")
    @ResponseBody()
    public ResponseEntity<Book> getBookById(@PathVariable("bookId") String bookId) {
        return bookService.getBookById(UUID.fromString(bookId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
