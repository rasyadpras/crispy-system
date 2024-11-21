package com.project.assesment.transaction.repository;

import com.project.assesment.transaction.entity.TransactionDetail;
import com.project.assesment.transaction.model.TransactionDetailResponse;
import com.project.assesment.transaction.model.TransactionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class TransactionDetailRepository {
    private final JdbcTemplate jdbcTemplate;

    public void create(TransactionDetail transactionDetail) {
        String query = "INSERT INTO transaction_details (transaction_id, product_id, quantity) VALUES (?, ?, ?)";
        jdbcTemplate.update(query, transactionDetail.getTransactionId(), transactionDetail.getProductId(), transactionDetail.getQuantity());
    }

    public List<TransactionDetail> findByTransactionId(Integer transactionId) {
        String query = "SELECT * FROM transaction_details WHERE transaction_id = ?";
        return jdbcTemplate.query(query, new Object[]{transactionId}, (rs, rowNum) -> {
            TransactionDetail transactionDetail = new TransactionDetail();
            transactionDetail.setId(rs.getInt("id"));
            transactionDetail.setProductId(rs.getInt("product_id"));
            transactionDetail.setQuantity(rs.getInt("quantity"));
            return transactionDetail;
        });
    }
}
