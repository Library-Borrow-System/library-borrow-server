package com.project.library.service;

import com.project.library.domain.Book;
import com.project.library.domain.Category;
import com.project.library.domain.Status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookService {

    Book createBook(String title, Category category, int price, String author, String isbn);

    List<Book> getAllBooks();
    List<Book> getBooksByStatus(Status status);

    Optional<Book> getBookById(UUID bookId);

    Optional<Book> updateBook(UUID bookId, Category category, int price, Status status);

    void deleteBook(UUID bookId);
}
