package com.project.library.domain;

import java.time.LocalDateTime;
import java.util.UUID;

public record BorrowingItem(
        UUID borrowId,
        UUID bookId,
        String title,
        String email,
        String phoneNum,
        LocalDateTime borrowAt,
        int term
) {
}
