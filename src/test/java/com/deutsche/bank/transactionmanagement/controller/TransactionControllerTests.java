package com.deutsche.bank.transactionmanagement.controller;

import com.deutsche.bank.transactionmanagement.model.Transaction;
import com.deutsche.bank.transactionmanagement.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionControllerTests {

    private MockMvc mockMvc;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = new Transaction();
        transaction.setId(1L);
        transaction.setAccountNumber("03052025");
        transaction.setTransactionType("Credit");
        transaction.setAmount(new BigDecimal("9500"));
        transaction.setTransactionTimestamp(LocalDateTime.now());
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testCreateTransactionSuccess() throws Exception {
        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"03052025\",\"transactionType\":\"Credit\",\"amount\":9500}"))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateTransactionFraudException() throws Exception {
        when(transactionService.createTransaction(any(Transaction.class)))
                .thenThrow(new RuntimeException("Fraud warning: Transaction exceeds threshold of 10,000 euros within 24 hours."));

        mockMvc.perform(post("/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"03052025\",\"transactionType\":\"Credit\",\"amount\":9500}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Fraud warning: Transaction exceeds threshold of 10,000 euros within 24 hours."));
    }

    @Test
    public void testGetTransactionByIdSuccess() throws Exception {
        when(transactionService.getTransactionById(anyLong())).thenReturn(transaction);

        mockMvc.perform(get("/transactions/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.accountNumber").value("03052025"));
    }

    @Test
    public void testGetTransactionByIdNotFound() throws Exception {
        when(transactionService.getTransactionById(111L)).thenReturn(null);

        mockMvc.perform(get("/transactions/111"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetTransactionsByAccountSuccess() throws Exception {
        when(transactionService.getTransactionsByAccount("03052025")).thenReturn(List.of(transaction));

        mockMvc.perform(get("/transactions/accounts/03052025"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].accountNumber").value("03052025"));
    }

    @Test
    public void testGetTransactionsByAccountEmpty() throws Exception {
        when(transactionService.getTransactionsByAccount("11111")).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/transactions/accounts/11111"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isEmpty());
    }

    @Test
    public void testDeleteTransactionSuccess() throws Exception {
        when(transactionService.deleteTransaction(1L)).thenReturn(true);

        mockMvc.perform(delete("/transactions/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTransactionForbidden() throws Exception {
        when(transactionService.deleteTransaction(111L)).thenReturn(false);

        mockMvc.perform(delete("/transactions/111"))
                .andExpect(status().isForbidden());
    }

}
