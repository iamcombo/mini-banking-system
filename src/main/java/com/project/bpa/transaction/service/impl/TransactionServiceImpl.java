package com.project.bpa.transaction.service.impl;

import com.project.bpa.account.entity.Account;
import com.project.bpa.account.repository.AccountRepository;
import com.project.bpa.authentication.user.entity.User;
import com.project.bpa.authentication.user.repository.UserRepository;
import com.project.bpa.exception.ApiResponse;
import com.project.bpa.exception.BadRequestException;
import com.project.bpa.exception.NotFoundException;
import com.project.bpa.transaction.dto.request.ListTransactionFilter;
import com.project.bpa.transaction.dto.request.DepositRequest;
import com.project.bpa.transaction.dto.request.TransferBalanceRequest;
import com.project.bpa.transaction.entity.Transaction;
import com.project.bpa.transaction.enums.TransactionStatusEnum;
import com.project.bpa.transaction.enums.TransactionTypeEnum;
import com.project.bpa.transaction.repository.TransactionRepository;
import com.project.bpa.transaction.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TransactionServiceImpl implements TransactionService {

    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public ApiResponse<String> transferBalance(String username, TransferBalanceRequest body) {
        Optional<Account> _senderAccount = accountRepository.findByAccountNumberForUpdate(body.getFromAccountNumber());
        Optional<Account> _destinationAccount = accountRepository.findByAccountNumberForUpdate(body.getToAccountNumber());

        if (body.getFromAccountNumber().equals(body.getToAccountNumber())) throw new BadRequestException("Account number must be different");
        if (_senderAccount.isEmpty()) throw new NotFoundException("Sender account not found");
        if (_destinationAccount.isEmpty()) throw new NotFoundException("Destination account not found");

        Account senderAccount = _senderAccount.get();
        Account destinationAccount = _destinationAccount.get();

        if (senderAccount.getBalance().compareTo(body.getAmount()) < 0) throw new BadRequestException("Insufficient balance");

        senderAccount.setBalance(senderAccount.getBalance().subtract(body.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(body.getAmount()));

        // Get user by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));

        Transaction transactionPayload = Transaction.builder()
                .fromAccountNumber(body.getFromAccountNumber())
                .toAccountNumber(body.getToAccountNumber())
                .amount(body.getAmount())
                .currency(body.getCurrency())
                .remark(body.getRemark())
                .proceedBy(user)
                .trxDate(LocalDateTime.now())
                .trxType(TransactionTypeEnum.TRANSFER)
                .trxStatus(TransactionStatusEnum.COMPLETED)
                .build();

        transactionRepository.save(transactionPayload);
        accountRepository.save(senderAccount);
        accountRepository.save(destinationAccount);

        return ApiResponse.success("Transfer successful");
    }

    @Override
    @Transactional
    public ApiResponse<String> depositBalance(String username, DepositRequest body) {
        Optional<Account> _account = accountRepository.findByAccountNumberForUpdate(body.getAccountNumber());

        if (_account.isEmpty()) throw new NotFoundException("Account not found");

        Account account = _account.get();

        // Validate currency consistency
        if (account.getCurrency() == null || body.getCurrency() == null || account.getCurrency() != body.getCurrency()) {
            throw new BadRequestException("Currency mismatch with account");
        }

        // Update balance
        account.setBalance(account.getBalance().add(body.getAmount()));

        // Get user by username
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new BadRequestException("User not found"));

        Transaction transactionPayload = Transaction.builder()
                .fromAccountNumber(body.getAccountNumber())
                .toAccountNumber(body.getAccountNumber())
                .amount(body.getAmount())
                .currency(body.getCurrency())
                .remark(body.getRemark())
                .proceedBy(user)
                .trxDate(LocalDateTime.now())
                .trxType(TransactionTypeEnum.DEPOSIT)
                .trxStatus(TransactionStatusEnum.COMPLETED)
                .build();

        transactionRepository.save(transactionPayload);
        accountRepository.save(account);

        return ApiResponse.success("Deposit successful");
    }

    @Override
    public ApiResponse<Page<Transaction>> getAdminTransactionHistory(ListTransactionFilter requestParam) {
        return null;
    }

    @Override
    public ApiResponse<Page<Transaction>> getUserTransactionHistory(String accountNumber, Pageable pageable) {
        return null;
    }

}
