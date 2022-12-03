package com.project.library.controller.api;

import com.project.library.controller.CreateBorrowRequest;
import com.project.library.domain.Borrow;
import com.project.library.service.BorrowService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

}
