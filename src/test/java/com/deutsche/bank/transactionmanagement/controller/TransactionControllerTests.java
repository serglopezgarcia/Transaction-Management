package com.deutsche.bank.transactionmanagement.controller;

import com.deutsche.bank.transactionmanagement.model.Transaction;
import com.deutsche.bank.transactionmanagement.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class TransactionControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAccountNumber("03052025");
        transaction.setTransactionType("Credit");
        transaction.setAmount(new BigDecimal("9500"));
        transaction.setTransactionTimestamp(LocalDateTime.now());
    }

    @Test
    public void testCreateTransactionSuccess() throws Exception {
        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"03052025\",\"transactionType\":\"Credit\",\"amount\":9500,\"transactionTimestamp\":\"2025-05-04T14:00:00\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateTransactionFraudException() throws Exception {
        when(transactionService.createTransaction(any(Transaction.class)))
                .thenThrow(new RuntimeException("Fraud warning: Transaction exceeds threshold of €10,000 within 24 hours."));

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"03052025\",\"transactionType\":\"Credit\",\"amount\":10500,\"transactionTimestamp\":\"2025-05-04T14:00:00\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Fraud warning: Transaction exceeds threshold of €10,000 within 24 hours."));
    }
}
