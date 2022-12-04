package com.project.library.controller.api;

import com.project.library.controller.CreateBorrowRequest;
import com.project.library.domain.Borrow;
import com.project.library.domain.BorrowingItem;
import com.project.library.service.BorrowService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class BorrowController {

    private final BorrowService borrowService;

    public BorrowController(BorrowService borrowService) {
        this.borrowService = borrowService;
    }

    @PostMapping("/api/v1/borrows")
    public Borrow createBorrow(@RequestBody CreateBorrowRequest borrowRequest) {
        return borrowService.createBorrow(
                borrowRequest.email(),
                borrowRequest.phoneNum(),
                borrowRequest.borrowItems()
        );
    }

    @GetMapping("/api/v1/borrows")
    public List<BorrowingItem> getBorrowingItem() {
        return borrowService.getBorrowingItem();
    }

    @PutMapping("/api/v1/borrow/return")
    public String returnBorrowingItem(@RequestParam(value = "borrowId") String borrowId, @RequestParam(value = "bookId") String bookId) {
        borrowService.returnBorrowItem(UUID.fromString(borrowId), UUID.fromString(bookId));
        return "반납되었습니다.";
    }

}
