package com.deutsche.bank.transactionmanagement.repository;

import com.deutsche.bank.transactionmanagement.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByAccountNumber(String accountNumber);

    @Query("SELECT SUM(t.amount) FROM Transaction t WHERE t.accountNumber = :accountNumber AND t.transactionTimestamp > :since")
    BigDecimal calculateTotalAmount(@Param("accountNumber") String accountNumber, @Param("since") LocalDateTime since);
}
