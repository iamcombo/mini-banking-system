package com.project.bpa.account.controller;

import com.project.bpa.account.dto.request.CreateAccountRequest;
import com.project.bpa.account.dto.response.AccountResponse;
import com.project.bpa.account.service.AccountService;
import com.project.bpa.exception.ApiResponse;
import com.project.bpa.security.user.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
@Tag(name = "Account", description = "Account endpoints")
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/create")
    @PreAuthorize("hasAuthority('CREATE_ACCOUNT')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Create account", description = "Create a new account")
    public ApiResponse<AccountResponse> createAccount(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestBody @Valid CreateAccountRequest body) {
        return accountService.createAccount(userDetails, body);
    }

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('VIEW_ALL_ACCOUNTS')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "List user accounts", description = "List all user accounts")
    public ApiResponse<List<AccountResponse>> listUserAccounts(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return accountService.listUserAccounts(userDetails);
    }

    @GetMapping("/detail/{accountNumber}")
    @PreAuthorize("hasAuthority('VIEW_ALL_ACCOUNTS')")
    @SecurityRequirement(name = "bearerAuth")
    @Operation(summary = "Get account by account number", description = "Get account by account number")
    public ApiResponse<AccountResponse> getAccountByAccountNumber(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable String accountNumber) {
        return accountService.getAccountByAccountNumber(userDetails, accountNumber);
    }
}
