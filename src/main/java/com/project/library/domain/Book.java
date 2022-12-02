package com.project.library.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public class Book {
    private final UUID bookId;
    private final String title;
    private Category category;
    private int price;
    private final String author;
    private Status status;
    private final String isbn;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Book(UUID bookId, String title, String author, String isbn, LocalDateTime createdAt) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.createdAt = createdAt;
    }

    public Book(UUID bookId, String title, Category category, int price, String author, Status status, String isbn, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.bookId = bookId;
        this.title = title;
        this.category = category;
        this.price = price;
        this.author = author;
        this.status = status;
        this.isbn = isbn;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public Category getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public String getAuthor() {
        return author;
    }

    public Status getStatus() {
        return status;
    }

    public String getIsbn() {
        return isbn;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
