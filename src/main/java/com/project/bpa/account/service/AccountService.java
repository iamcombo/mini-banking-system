package com.project.bpa.account.service;

import com.project.bpa.account.dto.request.CreateAccountRequest;
import com.project.bpa.account.entity.Account;
import com.project.bpa.exception.ApiResponse;

public interface AccountService {
    ApiResponse<Account> createAccount(String username, CreateAccountRequest body);
}
