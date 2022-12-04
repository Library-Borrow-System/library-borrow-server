package com.project.library.service;

import com.project.library.domain.Borrow;
import com.project.library.domain.BorrowItem;
import com.project.library.domain.BorrowingItem;
import com.project.library.repository.BorrowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DefaultBorrowService implements BorrowService {

    private final BorrowRepository borrowRepository;

    public DefaultBorrowService(BorrowRepository borrowRepository) {
        this.borrowRepository = borrowRepository;
    }

    @Override
    public Borrow createBorrow(String email, String phoneNum, List<BorrowItem> borrowItems) {
        Borrow borrow = new Borrow(UUID.randomUUID(), email, phoneNum, borrowItems, LocalDateTime.now(), null);
        return borrowRepository.insert(borrow);
    }

    @Override
    public List<BorrowingItem> getBorrowingItem() {
        return null;
    }

    @Override
    public BorrowItem returnBorrowItem(Borrow borrow, BorrowItem borrowItem) {
        return null;
    }
}
