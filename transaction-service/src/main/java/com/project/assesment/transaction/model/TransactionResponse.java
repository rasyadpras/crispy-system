package com.project.assesment.transaction.model;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class TransactionResponse {
    private Integer id;
    private LocalDate transactionDate;
    private Integer customerId;
    private List<TransactionDetailResponse> transactionDetails;
    private Long totalPrice;
}
