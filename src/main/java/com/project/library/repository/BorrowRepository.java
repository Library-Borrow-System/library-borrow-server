package com.project.library.repository;

import com.project.library.domain.Borrow;
import com.project.library.domain.BorrowItem;

import java.util.List;

public interface BorrowRepository {

    Borrow insert(Borrow borrow);

    List<Borrow> findAll();

    List<BorrowItem> findBorrowingItem();

    Borrow update(Borrow borrow);
}
