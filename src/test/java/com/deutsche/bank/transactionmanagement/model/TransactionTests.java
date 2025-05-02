package com.deutsche.bank.transactionmanagement.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTests {

    @Test
    public void testTransactionCreationObject() {
        Transaction transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAccountNumber("05052025");
        transaction.setTransactionType("Debit");
        transaction.setAmount(new BigDecimal("50"));
        transaction.setTransactionTimestamp(LocalDateTime.now());

        assertEquals(1L, transaction.getId());
        assertEquals("05052025", transaction.getAccountNumber());
        assertEquals("Debit", transaction.getTransactionType());
        assertEquals(new BigDecimal("50"), transaction.getAmount());
        assertNotNull(transaction.getTransactionTimestamp());
    }

    @Test
    public void testTransactionSpecificTimestamp() {
        LocalDateTime timestamp = LocalDateTime.of(2025, 5, 2, 13, 0);
        Transaction transaction = new Transaction();
        transaction.setTransactionTimestamp(timestamp);

        assertEquals(timestamp, transaction.getTransactionTimestamp());
    }
}

