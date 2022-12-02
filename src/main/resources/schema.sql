CREATE TABLE books
(
    book_id     BINARY(16)      PRIMARY KEY,
    title       VARCHAR(100)    NOT NULL UNIQUE,
    category    VARCHAR(50)     NOT NULL,
    price       bigint          NOT NULL,
    author      VARCHAR(50)     NOT NULL,
    status      VARCHAR(50)     NOT NULL,
    isbn        VARCHAR(100)    NOT NULL UNIQUE,
    created_at  datetime(6)     NOT NULL,
    updated_at  datetime(6)     DEFAULT NULL
);

CREATE TABLE borrows
(
    borrow_id   BINARY(16)      PRIMARY KEY,
    email       VARCHAR(100)    NOT NULL,
    phone_num   VARCHAR(50)     NOT NULL,
    created_at  datetime(6)     NOT NULL,
    updated_at  datetime(6)     DEFAULT NULL
);

CREATE TABLE borrow_items
(
    seq         bigint      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    borrow_id   binary(16)  NOT NULL,
    book_id     binary(16)  NOT NULL,
    fee         int         DEFAULT NULL,
    term        int         NOT NULL,
    borrow_at   datetime(6) NOT NULL,
    return_at   datetime(6) DEFAULT NULL,
    INDEX(borrow_id),
    CONSTRAINT fk_borrow_items_to_borrow FOREIGN KEY (borrow_id) REFERENCES borrows (borrow_id) ON DELETE CASCADE,
    CONSTRAINT fk_borrow_items_to_book FOREIGN KEY (book_id) REFERENCES  books (book_id)
);