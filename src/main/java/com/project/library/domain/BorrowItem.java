package com.project.library.domain;

import java.util.UUID;

public class BorrowItem {
    private final UUID bookId;
    private int fee;
    private final int term;

    public BorrowItem(UUID bookId, int fee, int term) {
        this.bookId = bookId;
        this.fee = fee;
        this.term = term;
    }

    public UUID getBookId() {
        return bookId;
    }

    public int getFee() {
        return fee;
    }

    public int getTerm() {
        return term;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
