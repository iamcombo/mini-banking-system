package com.project.bpa.account.service.impl;

import com.project.bpa.account.dto.request.CreateAccountRequest;
import com.project.bpa.account.entity.Account;
import com.project.bpa.account.repository.AccountRepository;
import com.project.bpa.account.service.AccountService;
import com.project.bpa.authentication.user.entity.User;
import com.project.bpa.authentication.user.repository.UserRepository;
import com.project.bpa.exception.ApiResponse;
import com.project.bpa.exception.BadRequestException;
import com.project.bpa.utils.AccountUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AccountUtil accountUtil;

    @Override
    public ApiResponse<Account> createAccount(CreateAccountRequest body) {
        // Check user is exists
        Optional<User> user = userRepository.findById(body.getUserId());
        if (user.isEmpty()) {
            throw new BadRequestException("User not found");
        }

        // Check phone number and email is unique
        Optional<Account> accountByPhone = accountRepository.findByAccountHolderPhone(body.getAccountHolderPhone());
        Optional<Account> accountByEmail = accountRepository.findByAccountHolderEmail(body.getAccountHolderEmail());
        if (accountByPhone.isPresent() || accountByEmail.isPresent()) {
            throw new BadRequestException("Account already exists");
        }

        // Generate account number
        String accountNumber = accountUtil.generateAccountNumber();

        // Create account
        Account account = Account.builder()
                .accountNumber(accountNumber)
                .accountHolderName(body.getAccountHolderName())
                .accountHolderPhone(body.getAccountHolderPhone())
                .accountHolderEmail(body.getAccountHolderEmail())
                .accountType(body.getAccountType())
                .balance(BigDecimal.ZERO)
                .nationalId(body.getNationalId())
                .user(user.get())
                .build();

        // Save account
        Account savedAccount = accountRepository.save(account);

        return ApiResponse.success(savedAccount);
    }
}
