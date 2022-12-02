package com.project.library.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

public class BorrowingItem {
    private final UUID bookId;
    private final UUID borrowId;
    private final LocalDateTime borrowAt;
    private final int fee;
    private final int term;

    public BorrowingItem(UUID bookId, UUID borrowId, LocalDateTime borrowAt, int term) {
        this.bookId = bookId;
        this.borrowId = borrowId;
        this.borrowAt = borrowAt;
        this.fee = calculateFee(borrowAt, term);
        this.term = term;
    }

    private int calculateFee(LocalDateTime borrowAt, int term) {
        long between = ChronoUnit.DAYS.between(borrowAt, LocalDateTime.now());
        if (between > term) {
            return (int)between - term;
        }
        return 0;
    }
}
