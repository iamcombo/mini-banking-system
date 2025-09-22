package com.project.bpa.account.repository;

import com.project.bpa.account.entity.Account;
import com.project.bpa.common.repository.BaseRepository;

import java.util.Optional;

public interface AccountRepository extends BaseRepository<Account, Long> {
    Optional<Account> findByAccountHolderPhone(String accountHolderPhone);
    Optional<Account> findByAccountHolderEmail(String accountHolderEmail);
}
