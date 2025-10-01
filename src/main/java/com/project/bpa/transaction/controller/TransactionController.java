package com.project.bpa.transaction.controller;

import com.project.bpa.exception.ApiResponse;
import com.project.bpa.transaction.dto.request.TransferBalanceRequest;
import com.project.bpa.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction")
@Tag(name = "Transaction", description = "Transaction endpoints")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Transfer balance", description = "Transfer balance")
    public ApiResponse<String> transferBalance(@RequestBody @Valid TransferBalanceRequest body) {
        return transactionService.TransferBalance(body);
    }

//    @GetMapping("/history")
//    @SecurityRequirement(name = "bearerAuth")
//    @Operation(summary = "Get transaction history", description = "Get transaction history")
//    public ApiResponse<> listTransactionHistory(@RequestParam String accountNumber) {
//        return transactionService.getUserTransactionHistory(accountNumber);
//    }
}
