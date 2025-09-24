package com.project.bpa.transaction.controller;

import com.project.bpa.exception.ApiResponse;
import com.project.bpa.transaction.dto.request.TransferBalanceRequest;
import com.project.bpa.transaction.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    public ApiResponse<String> transferBalance(@RequestBody @Valid TransferBalanceRequest body) {
        return transactionService.TransferBalance(body);
    }
}
