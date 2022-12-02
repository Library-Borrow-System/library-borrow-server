package com.project.library.controller;

import com.project.library.domain.Category;
import com.project.library.domain.Status;

import java.util.UUID;

public record UpdateBookRequest(
        Category category,
        int price,
        Status status
) {
}
