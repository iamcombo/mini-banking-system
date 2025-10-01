package com.project.bpa.account.controller;


import com.project.bpa.account.dto.request.CreateAccountRequest;
import com.project.bpa.account.entity.Account;
import com.project.bpa.account.service.AccountService;
import com.project.bpa.exception.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ApiResponse<Account> createAccount(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody @Valid CreateAccountRequest body) {
        String username = userDetails.getUsername();
        return accountService.createAccount(username, body);
    }
}
