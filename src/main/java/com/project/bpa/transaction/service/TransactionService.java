package com.project.bpa.transaction.service;

import com.project.bpa.exception.ApiResponse;
import com.project.bpa.security.user.CustomUserDetails;
import com.project.bpa.transaction.dto.request.DepositRequest;
import com.project.bpa.transaction.dto.request.TransferBalanceRequest;
import com.project.bpa.transaction.dto.request.WithdrawRequest;
import com.project.bpa.transaction.entity.Transaction;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TransactionService {
    ApiResponse<String> transferBalance(String username, TransferBalanceRequest body);
    ApiResponse<String> depositBalance(String username, DepositRequest body);
    ApiResponse<String> withdrawBalance(CustomUserDetails userDetails, WithdrawRequest body);
    ApiResponse<List<Transaction>> getUserTransactionHistory(String accountNumber, Pageable pageable);
}
