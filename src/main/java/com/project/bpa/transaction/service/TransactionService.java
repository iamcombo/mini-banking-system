package com.project.bpa.transaction.service;

import com.project.bpa.exception.ApiResponse;
import com.project.bpa.transaction.dto.request.ListTransactionFilter;
import com.project.bpa.transaction.dto.request.TransferBalanceRequest;
import com.project.bpa.transaction.entity.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;

public interface TransactionService {
    ApiResponse<String> TransferBalance(TransferBalanceRequest body);
    ApiResponse<Page<Transaction>> getAdminTransactionHistory(ListTransactionFilter requestParam);
    ApiResponse<Page<Transaction>> getUserTransactionHistory(String accountNumber, Pageable pageable);
}
