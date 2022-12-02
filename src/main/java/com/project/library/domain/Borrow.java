package com.project.library.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Borrow {
    private final UUID borrowId;
    private final String email;
    private final String phoneNum;
    private final List<BorrowItem> borrowItems;
    private final LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Borrow(UUID borrowId, String email, String phoneNum, List<BorrowItem> borrowItems, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.borrowId = borrowId;
        this.email = email;
        this.phoneNum = phoneNum;
        this.borrowItems = borrowItems;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getBorrowId() {
        return borrowId;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public List<BorrowItem> getBorrowItems() {
        return borrowItems;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
