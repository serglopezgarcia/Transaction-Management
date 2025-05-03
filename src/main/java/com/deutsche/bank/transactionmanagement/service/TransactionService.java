package com.deutsche.bank.transactionmanagement.service;

import com.deutsche.bank.transactionmanagement.model.Transaction;

import java.util.List;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);
    Transaction getTransactionById(Long id);
    List<Transaction> getTransactionsByAccount(String accountNumber);
}
