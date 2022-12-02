package com.project.library.domain;

import java.util.UUID;

public record BorrowItem(UUID bookId, int price, int term) {
}
