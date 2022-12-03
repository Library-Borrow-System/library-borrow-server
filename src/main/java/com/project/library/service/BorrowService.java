package com.project.library.service;

import com.project.library.domain.Borrow;
import com.project.library.domain.BorrowItem;
import com.project.library.domain.BorrowingItem;

import java.util.List;

public interface BorrowService {

    Borrow createBorrow(String email, String phoneNum, List<BorrowItem> borrowItems);

    List<BorrowingItem> getBorrowingItem();

    BorrowItem returnBorrowItem(Borrow borrow, BorrowItem borrowItem);
}
