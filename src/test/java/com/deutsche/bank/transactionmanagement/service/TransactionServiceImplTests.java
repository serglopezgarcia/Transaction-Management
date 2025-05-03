package com.deutsche.bank.transactionmanagement.service;


import com.deutsche.bank.transactionmanagement.model.Transaction;
import com.deutsche.bank.transactionmanagement.repository.TransactionRepository;
import com.deutsche.bank.transactionmanagement.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TransactionServiceImplTests {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService = new TransactionServiceImpl(); // Use the interface type

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAccountNumber("03052025");
        transaction.setTransactionType("Credit");
        transaction.setAmount(new BigDecimal("1000"));
        transaction.setTransactionTimestamp(LocalDateTime.now());
    }

    @Test
    public void testCreateTransaction() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);
        Transaction createdTransaction = transactionService.createTransaction(transaction);
        assertNotNull(createdTransaction);
        assertEquals("03052025", createdTransaction.getAccountNumber());
    }

    @Test
    public void testGetTransactionById() {
        when(transactionRepository.findById(1L)).thenReturn(Optional.of(transaction));
        Transaction foundTransaction = transactionService.getTransactionById(1L);
        assertNotNull(foundTransaction);
        assertEquals(1L, foundTransaction.getId());
    }

    @Test
    public void testGetTransactionByIdNonExistent() {
        when(transactionRepository.findById(111L)).thenReturn(Optional.empty());
        Transaction foundTransaction = transactionService.getTransactionById(111L);
        assertNull(foundTransaction);
    }

}
