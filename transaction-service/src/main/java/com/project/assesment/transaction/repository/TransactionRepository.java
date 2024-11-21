package com.project.assesment.transaction.repository;

import com.project.assesment.transaction.entity.Transaction;
import com.project.assesment.transaction.model.TransactionRequest;
import com.project.assesment.transaction.model.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    public void create(Transaction transaction) {
        String query = "INSERT INTO transactions (transaction_date, customer_id) VALUES (?, ?)";
        jdbcTemplate.update(query, transaction.getTransactionDate(), transaction.getCustomerId());
    }

    public Optional<Transaction> findById(String id) {
        String query = "SELECT * FROM transactions WHERE id = ?";
        try {
            Transaction data = jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setCustomerId(rs.getInt("customer_id"));
                transaction.setTransactionDate(rs.getDate("transaction_date").toLocalDate());
                transaction.setTotalPrice(rs.getLong("total_price"));
                return transaction;
            });
            return Optional.ofNullable(data);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
