package com.project.bpa.transaction.controller;

import com.project.bpa.exception.ApiResponse;
import com.project.bpa.security.user.CustomUserDetails;
import com.project.bpa.transaction.dto.request.DepositRequest;
import com.project.bpa.transaction.dto.request.WithdrawRequest;
import com.project.bpa.transaction.entity.Transaction;
import com.project.bpa.transaction.dto.request.TransferBalanceRequest;
import com.project.bpa.transaction.service.TransactionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/transaction")
@Tag(name = "Transaction", description = "Transaction endpoints")
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping("/transfer")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Transfer balance", description = "Transfer balance")
    public ApiResponse<String> transferBalance(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid TransferBalanceRequest body
    ) {
        String username = userDetails.getUsername();
        return transactionService.transferBalance(username, body);
    }

    @PostMapping("/deposit")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Deposit balance", description = "Deposit balance to an account")
    public ApiResponse<String> depositBalance(
        @AuthenticationPrincipal UserDetails userDetails,
        @RequestBody @Valid DepositRequest body
    ) {
        String username = userDetails.getUsername();
        return transactionService.depositBalance(username, body);
    }

    @PostMapping("/withdraw")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Withdraw balance", description = "Withdraw balance from an account")
    public ApiResponse<String> withdrawBalance(
        @AuthenticationPrincipal CustomUserDetails userDetails,
        @RequestBody @Valid WithdrawRequest body
    ) {
        return transactionService.withdrawBalance(userDetails, body);
    }

    @GetMapping("/history")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get transaction history", description = "Get transaction history for an account number")
    public ApiResponse<List<Transaction>> listTransactionHistory(
        @RequestParam String accountNumber,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return transactionService.getUserTransactionHistory(accountNumber, pageable);
    }
}
