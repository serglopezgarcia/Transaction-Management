package com.deutsche.bank.transactionmanagement.service.impl;

import com.deutsche.bank.transactionmanagement.model.Transaction;
import com.deutsche.bank.transactionmanagement.repository.TransactionRepository;
import com.deutsche.bank.transactionmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }
}
