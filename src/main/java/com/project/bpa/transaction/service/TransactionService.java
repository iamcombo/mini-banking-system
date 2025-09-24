package com.project.bpa.transaction.service;

import com.project.bpa.exception.ApiResponse;
import com.project.bpa.transaction.dto.request.TransferBalanceRequest;

public interface TransactionService {
    ApiResponse<String> TransferBalance(TransferBalanceRequest body);
}
