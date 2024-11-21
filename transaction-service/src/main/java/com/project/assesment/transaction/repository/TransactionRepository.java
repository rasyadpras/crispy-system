package com.project.assesment.transaction.repository;

import com.project.assesment.transaction.entity.Transaction;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TransactionRepository {
    private final JdbcTemplate jdbcTemplate;

    public Integer create(Transaction transaction) {
        String query = "INSERT INTO transactions (transaction_date, customer_id) VALUES (?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(query, Integer.class, transaction.getTransactionDate(), transaction.getCustomerId());
    }

    public Optional<Transaction> findById(Integer id) {
        String query = "SELECT * FROM transactions WHERE id = ?";
        try {
            Transaction data = jdbcTemplate.queryForObject(query, new Object[]{id}, (rs, rowNum) -> {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setCustomerId(rs.getInt("customer_id"));
                transaction.setTransactionDate(rs.getDate("transaction_date").toLocalDate());
                return transaction;
            });
            return Optional.ofNullable(data);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
