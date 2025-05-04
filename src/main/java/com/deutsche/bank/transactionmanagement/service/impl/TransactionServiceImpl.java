package com.deutsche.bank.transactionmanagement.service.impl;

import com.deutsche.bank.transactionmanagement.model.Transaction;
import com.deutsche.bank.transactionmanagement.repository.TransactionRepository;
import com.deutsche.bank.transactionmanagement.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction createTransaction(Transaction transaction) {
        checkFraud(transaction);
        return transactionRepository.save(transaction);
    }

    private void checkFraud(Transaction transaction) {
        LocalDateTime withinTwentyFourHours = LocalDateTime.now().minusHours(24);
        BigDecimal totalAmount = transactionRepository.calculateTotalAmount(transaction.getAccountNumber(), withinTwentyFourHours);

        if (totalAmount == null) {
            totalAmount = BigDecimal.ZERO;
        }

        if (totalAmount.add(transaction.getAmount()).compareTo(new BigDecimal("10000")) > 0) {
            throw new RuntimeException("Fraud warning: Transaction exceeds threshold of 10,000 euros within 24 hours.");
        }
    }

    @Override
    public Transaction getTransactionById(Long id) {
        return transactionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Transaction> getTransactionsByAccount(String accountNumber) {
        return transactionRepository.findByAccountNumber(accountNumber);
    }

    @Override
    public boolean deleteTransaction(Long id) {
        Optional<Transaction> transactionOpt = transactionRepository.findById(id);
        if (transactionOpt.isPresent()) {
            Transaction transaction = transactionOpt.get();
            LocalDateTime twentyFourHoursAgo = LocalDateTime.now().minusHours(24);
            if (transaction.getTransactionTimestamp().isAfter(twentyFourHoursAgo)) {
                transactionRepository.delete(transaction);
                return true;
            }
        }
        return false;
    }
}
