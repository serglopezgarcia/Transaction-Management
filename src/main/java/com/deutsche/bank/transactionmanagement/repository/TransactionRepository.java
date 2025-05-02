package com.deutsche.bank.transactionmanagement.repository;

import com.deutsche.bank.transactionmanagement.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountNumber(String accountNumber);
}
