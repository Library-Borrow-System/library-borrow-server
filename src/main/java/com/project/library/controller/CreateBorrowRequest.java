package com.project.library.controller;

import com.project.library.domain.BorrowItem;

import java.util.List;

public record CreateBorrowRequest(String email, String phoneNum, List<BorrowItem> borrowItems) {
}
