package com.project.library.repository;

import com.project.library.domain.Borrow;

import java.util.List;

public interface BorrowRepository {

    Borrow insert(Borrow borrow);

    List<Borrow> findAll();

    Borrow update(Borrow borrow);
}
