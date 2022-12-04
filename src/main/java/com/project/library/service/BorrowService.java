package com.project.library.service;

import com.project.library.domain.Borrow;
import com.project.library.domain.BorrowItem;
import com.project.library.domain.BorrowingItem;

import java.util.List;
import java.util.UUID;

public interface BorrowService {

    Borrow createBorrow(String email, String phoneNum, List<BorrowItem> borrowItems);

    List<BorrowingItem> getBorrowingItem();

    void returnBorrowItem(UUID borrowId, UUID bookId);
}
