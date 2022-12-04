package com.project.library.utils;

public enum Queries {
    // Book
    BOOK_INSERT_SQL("INSERT INTO books (book_id, title, category, price, author, status, isbn, created_at, updated_at) VALUES (UUID_TO_BIN(:bookId), :title, :category, :price, :author, :status, :isbn, :createdAt, :updatedAt)"),
    BOOK_FIND_ALL_SQL("SELECT * FROM books"),
    BOOK_FIND_BY_STATUS_SQL("SELECT * FROM books WHERE status = :status"),
    BOOK_FIND_BY_ID_SQL("SELECT * FROM books WHERE book_id = UUID_TO_BIN(:bookId)"),
    BOOK_UPDATE_SQL("UPDATE books SET category = :category, price = :price, status = :status, updated_at = :updatedAt WHERE book_id = UUID_TO_BIN(:bookId)"),
    BOOK_UPDATE_STATUS_SQL("UPDATE books SET status = :status WHERE book_id = UUID_TO_BIN(:bookId)"),
    BOOK_DELETE_BY_ID("DELETE FROM books WHERE book_id = UUID_TO_BIN(:bookId)"),

    // Borrow
    BORROW_INSERT_SQL("INSERT INTO borrows (borrow_id, email, phone_num, created_at, updated_at) VALUES (UUID_TO_BIN(:borrowId), :email, :phoneNum, :createdAt, :updatedAt)"),
    BORROW_FIND_ALL_SQL("SELECT * FROM borrows"),
    BORROW_UPDATE_SQL("UPDATE borrows SET updated_at = :updatedAt WHERE borrow_id = UUID_TO_BIN(:borrowId)"),

    // BorrowItem
    BORROW_ITEM_INSERT_SQL("INSERT INTO borrow_items (borrow_id, book_id, fee, term, borrow_at, return_at) VALUES (UUID_TO_BIN(:borrowId), UUID_TO_BIN(:bookId), :fee, :term, :borrowAt, :returnAt)"),
    BORROW_ITEM_FIND_BY_BORROW_ID_SQL("SELECT * FROM borrow_items WHERE borrow_id = UUID_TO_BIN(:borrowId)"),
    BORROW_ITEM_FIND_BORROWING_SQL("SELECT B.title as title, C.email as email, C.phone_num as phone_num, A.borrow_at as borrow_at, A.term as term, A.borrow_id as borrow_id, A.book_id as book_id " +
            "FROM borrow_items A JOIN books B ON A.book_id = B.book_id JOIN borrows C ON A.borrow_id = C.borrow_id " +
            "WHERE B.status = :status"),
    BORROW_ITEM_UPDATE_SQL("UPDATE borrow_items SET fee = :fee, return_at = :returnAt WHERE borrow_id = UUID_TO_BIN(:borrowId) AND book_id = UUID_TO_BIN(:bookId)");
    private final String sql;

    Queries(String sql) {
        this.sql = sql;
    }

    public String getQuery() {
        return sql;
    }
}
