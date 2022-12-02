package com.project.library.service;

import com.project.library.domain.Book;
import com.project.library.domain.Category;
import com.project.library.domain.Status;
import com.project.library.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DefaultBookService implements BookService {

    private final BookRepository bookRepository;

    public DefaultBookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book createBook(String title, Category category, int price, String author, String isbn) {
        Book book = new Book(UUID.randomUUID(), title, category, price, author, Status.BORROW_POSSIBLE, isbn, LocalDateTime.now(), null);
        return bookRepository.insert(book);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(UUID bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public Optional<Book> updateBook(UUID bookId, Category category, int price, Status status) {
        Optional<Book> maybeBook = getBookById(bookId);
        if (maybeBook.isEmpty()) {
            return Optional.empty();
        }
        Book book = new Book(
                bookId,
                maybeBook.get().getTitle(),
                category,
                price,
                maybeBook.get().getAuthor(),
                status,
                maybeBook.get().getIsbn(),
                maybeBook.get().getCreatedAt(),
                LocalDateTime.now()
        );
        return Optional.of(bookRepository.update(book));
    }

    @Override
    public void deleteBook(UUID bookId) {
        bookRepository.deleteById(bookId);
    }
}
