package com.project.bpa.account.controller;


import com.project.bpa.account.dto.request.CreateAccountRequest;
import com.project.bpa.account.entity.Account;
import com.project.bpa.account.service.AccountService;
import com.project.bpa.exception.ApiResponse;
import com.project.bpa.security.annotation.RequiresPermission;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    @RequiresPermission("account:create")
    public ApiResponse<Account> createAccount(@RequestBody @Valid CreateAccountRequest body) {
        return accountService.createAccount(body);
    }
}
