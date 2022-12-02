package com.project.library.utils;

public enum Queries {
    // Book
    BOOK_INSERT_SQL("INSERT INTO books (book_id, title, category, price, author, status, isbn, created_at, updated_at) VALUES (UUID_TO_BIN(:bookId), :title, :category, :price, :author, :status, :isbn, :createdAt, :updatedAt)"),
    BOOK_FIND_ALL_SQL("SELECT * FROM books"),
    BOOK_FIND_BY_ID_SQL("SELECT * FROM books WHERE book_id = UUID_TO_BIN(:bookId)"),
    BOOK_UPDATE_SQL("UPDATE books SET category = :category, price = :price, status = :status, updated_at = :updatedAt WHERE book_id = UUID_TO_BIN(:bookId)");

    private final String sql;

    Queries(String sql) {
        this.sql = sql;
    }

    public String getQuery() {
        return sql;
    }
}
