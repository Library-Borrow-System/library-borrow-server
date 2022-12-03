package com.project.library.controller.api;

import com.project.library.controller.CreateBookRequest;
import com.project.library.controller.UpdateBookRequest;
import com.project.library.domain.Book;
import com.project.library.domain.Status;
import com.project.library.service.BookService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Optional;
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
    public List<Book> getAllBooks(@RequestParam(value = "status", required = false) String status) {
        if (StringUtils.isEmpty(status)) {
            return bookService.getAllBooks();
        }
        return bookService.getBooksByStatus(Status.valueOf(status));
    }

    @GetMapping("/api/v1/book/{bookId}")
    @ResponseBody
    public ResponseEntity<Book> getBookById(@PathVariable("bookId") String bookId) {
        return bookService.getBookById(UUID.fromString(bookId))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/api/v1/book/{bookId}")
    @ResponseBody
    public ResponseEntity<Book> updateBook(@PathVariable("bookId") String bookId, @RequestBody UpdateBookRequest bookRequest) {
        Optional<Book> book = bookService.updateBook(
                UUID.fromString(bookId),
                bookRequest.category(),
                bookRequest.price(),
                bookRequest.status()
        );
        return book.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/v1/book/{bookId}")
    public String deleteBook(@PathVariable("bookId") String bookId) {
        bookService.deleteBook(UUID.fromString(bookId));
        return "해당 값이 삭제되었습니다";
    }
}
