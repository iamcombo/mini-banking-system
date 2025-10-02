package com.project.bpa.account.service.impl;

import com.project.bpa.account.dto.request.CreateAccountRequest;
import com.project.bpa.account.dto.response.AccountResponse;
import com.project.bpa.account.entity.Account;
import com.project.bpa.account.repository.AccountRepository;
import com.project.bpa.account.service.AccountService;
import com.project.bpa.authentication.user.repository.UserRepository;
import com.project.bpa.common.enums.CurrencyEnum;
import com.project.bpa.exception.ApiResponse;
import com.project.bpa.exception.BadRequestException;
import com.project.bpa.security.user.CustomUserDetails;
import com.project.bpa.utils.AccountUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public ApiResponse<AccountResponse> createAccount(CustomUserDetails userDetails, CreateAccountRequest body) {
        // Check phone number and email is unique
        Optional<Account> accountByPhone = accountRepository.findByAccountHolderPhone(body.getAccountHolderPhone());
        Optional<Account> accountByEmail = accountRepository.findByAccountHolderEmail(body.getAccountHolderEmail());
        if (accountByPhone.isPresent() || accountByEmail.isPresent()) {
            throw new BadRequestException("Account already exists");
        }

        // Generate account number
        String accountNumber = AccountUtil.generateAccountNumber();

        // Create account
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .accountHolderName(body.getAccountHolderName())
                .accountHolderPhone(body.getAccountHolderPhone())
                .accountHolderEmail(body.getAccountHolderEmail())
                .accountType(body.getAccountType())
                .balance(BigDecimal.ZERO)
                .nationalId(body.getNationalId())
                .currency(CurrencyEnum.USD)
                .user(userDetails.getUser())
                .build();

        // Save account
        Account savedAccount = accountRepository.save(account);
        AccountResponse createAccountResponse = AccountResponse.builder()
                .id(savedAccount.getId())
                .accountHolderName(savedAccount.getAccountHolderName())
                .accountHolderEmail(savedAccount.getAccountHolderEmail())
                .accountHolderPhone(savedAccount.getAccountHolderPhone())
                .nationalId(savedAccount.getNationalId())
                .accountNumber(savedAccount.getAccountNumber())
                .balance(savedAccount.getBalance())
                .accountType(savedAccount.getAccountType())
                .currency(savedAccount.getCurrency())
                .build();

        return ApiResponse.successCreated(createAccountResponse);
    }

    @Override
    public ApiResponse<List<AccountResponse>> listUserAccounts(CustomUserDetails userDetails) {
        List<Account> accounts = accountRepository.findAllByUserUsername(userDetails.getUsername());
        List<AccountResponse> accountResponses = accounts.stream().map(account -> AccountResponse.builder()
                .id(account.getId())
                .accountHolderName(account.getAccountHolderName())
                .accountHolderEmail(account.getAccountHolderEmail())
                .accountHolderPhone(account.getAccountHolderPhone())
                .nationalId(account.getNationalId())
                .accountNumber(account.getAccountNumber())
                .balance(account.getBalance())
                .accountType(account.getAccountType())
                .currency(account.getCurrency())
                .build()).toList();

        return ApiResponse.success(accountResponses);
    }

    @Override
    public ApiResponse<AccountResponse> getAccountByAccountNumber(CustomUserDetails userDetails, String accountNumber) {
        Optional<Account> _account = accountRepository.findByAccountNumber(accountNumber);
        if (_account.isEmpty()) throw new BadRequestException("Account not found");

        Account account = _account.get();
        AccountResponse accountResponse = AccountResponse.builder()
            .id(account.getId())
            .accountHolderName(account.getAccountHolderName())
            .accountHolderEmail(account.getAccountHolderEmail())
            .accountHolderPhone(account.getAccountHolderPhone())
            .nationalId(account.getNationalId())
            .accountNumber(account.getAccountNumber())
            .balance(account.getBalance())
            .accountType(account.getAccountType())
            .currency(account.getCurrency())
            .build();

        return ApiResponse.success(accountResponse);
    }
}
