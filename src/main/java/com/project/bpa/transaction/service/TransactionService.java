package com.project.bpa.transaction.service;

import com.project.bpa.exception.ApiResponse;
import com.project.bpa.transaction.dto.request.ListTransactionFilter;
import com.project.bpa.transaction.dto.request.DepositRequest;
import com.project.bpa.transaction.dto.request.TransferBalanceRequest;
import com.project.bpa.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    ApiResponse<String> transferBalance(String username, TransferBalanceRequest body);
    ApiResponse<String> depositBalance(String username, DepositRequest body);
    ApiResponse<Page<Transaction>> getAdminTransactionHistory(ListTransactionFilter requestParam);
    ApiResponse<Page<Transaction>> getUserTransactionHistory(String accountNumber, Pageable pageable);
}
