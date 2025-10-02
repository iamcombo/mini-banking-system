package com.project.bpa.account.service;

import com.project.bpa.account.dto.request.CreateAccountRequest;
import com.project.bpa.account.dto.response.AccountResponse;
import com.project.bpa.exception.ApiResponse;
import com.project.bpa.security.user.CustomUserDetails;

import java.util.List;

public interface AccountService {
    ApiResponse<AccountResponse> createAccount(CustomUserDetails userDetails, CreateAccountRequest body);
    ApiResponse<List<AccountResponse>> listUserAccounts(CustomUserDetails userDetails);
    ApiResponse<AccountResponse> getAccountByAccountNumber(CustomUserDetails userDetails, String accountNumber);
}
