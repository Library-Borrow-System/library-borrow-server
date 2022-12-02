package com.project.library.controller;

import com.project.library.domain.Category;

public record CreateBookRequest(
        String title,
        Category category,
        int price,
        String author,
        String isbn
) {
}
