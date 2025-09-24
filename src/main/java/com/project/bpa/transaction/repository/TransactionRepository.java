package com.project.bpa.transaction.repository;

import com.project.bpa.common.repository.BaseRepository;
import com.project.bpa.transaction.entity.Transaction;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends BaseRepository<Transaction, Long> {
}
