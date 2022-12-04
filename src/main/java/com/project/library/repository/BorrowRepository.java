package com.project.library.repository;

import com.project.library.domain.Borrow;
import com.project.library.domain.BorrowItem;
import com.project.library.domain.BorrowingItem;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface BorrowRepository {

    Borrow insert(Borrow borrow);

    List<Borrow> findAll();

    List<BorrowingItem> findBorrowingItem();

    BorrowItem update(Borrow borrow, BorrowItem borrowItem);
}
