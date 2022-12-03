package com.project.library.repository;

import com.project.library.domain.Book;
import com.project.library.domain.Status;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookRepository {

    Book insert(Book book);

    List<Book> findAll();
    List<Book> findByStatus(Status status);

    Optional<Book> findById(UUID bookId);

    Book update(Book book);

    void deleteById(UUID bookId);
}
