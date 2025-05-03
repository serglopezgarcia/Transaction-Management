package com.deutsche.bank.transactionmanagement.service;

import com.deutsche.bank.transactionmanagement.model.Transaction;

public interface TransactionService {

    Transaction createTransaction(Transaction transaction);
}
