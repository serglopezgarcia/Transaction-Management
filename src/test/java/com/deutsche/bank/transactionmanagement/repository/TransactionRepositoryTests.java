package com.deutsche.bank.transactionmanagement.repository;

import com.deutsche.bank.transactionmanagement.model.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class TransactionRepositoryTests {

    @Autowired
    private TransactionRepository transactionRepository;

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = new Transaction();
        transaction.setAccountNumber("02052025");
        transaction.setTransactionType("Debit");
        transaction.setAmount(new BigDecimal("50"));
        transaction.setTransactionTimestamp(LocalDateTime.now());
        transactionRepository.save(transaction);
    }

    @Test
    public void testFindByAccountNumber() {
        List<Transaction> transactions = transactionRepository.findByAccountNumber("02052025");
        assertFalse(transactions.isEmpty());
        assertEquals("02052025", transactions.get(0).getAccountNumber());
    }

    @Test
    public void testFindByAccountNumberNonExistent() {
        List<Transaction> transactions = transactionRepository.findByAccountNumber("11111");
        assertTrue(transactions.isEmpty());
    }

    @Test
    public void testFindById() {
        Transaction transaction = transactionRepository.findById(this.transaction.getId()).orElse(null);
        assertNotNull(transaction);
        assertEquals(this.transaction.getId(), transaction.getId());
    }

    @Test
    public void testFindByIdNonExistent() {
        Transaction transaction = transactionRepository.findById(99L).orElse(null);
        assertNull(transaction);
    }

    @Test
    public void testSaveTransaction() {
        Transaction newTransaction = new Transaction();
        newTransaction.setAccountNumber("02052025");
        newTransaction.setTransactionType("Debit");
        newTransaction.setAmount(new BigDecimal("50"));
        newTransaction.setTransactionTimestamp(LocalDateTime.now());
        Transaction savedTransaction = transactionRepository.save(newTransaction);
        assertNotNull(savedTransaction.getId());
        assertEquals("02052025", savedTransaction.getAccountNumber());
    }

    @Test
    public void testDeleteTransaction() {
        assertNotNull(transaction);
        transactionRepository.delete(transaction);
        Transaction deletedTransaction = transactionRepository.findById(transaction.getId()).orElse(null);
        assertNull(deletedTransaction);
    }

    @Test
    public void testCalculateTotalAmount() {
        LocalDateTime withinTwentyFourHours = LocalDateTime.now().minusHours(24);
        BigDecimal totalAmount = transactionRepository.calculateTotalAmount(transaction.getAccountNumber(), withinTwentyFourHours);
        assertEquals(new BigDecimal("50"), totalAmount);
    }

}
