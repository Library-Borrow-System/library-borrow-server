package com.project.library.domain;

import java.util.UUID;

public record BorrowItem(UUID bookId, int fee, int term) {
}
