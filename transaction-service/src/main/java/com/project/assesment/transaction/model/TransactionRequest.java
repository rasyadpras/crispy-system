package com.project.assesment.transaction.model;

import lombok.Data;

import java.util.List;

@Data
public class TransactionRequest {
    private String transactionDate;
    private Integer customerId;
    private List<TransactionDetailRequest> transactionDetails;
}
