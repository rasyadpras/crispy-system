package com.project.assesment.transaction.entity;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class Transaction {
    private Integer id;
    private LocalDate transactionDate;
    private Integer customerId;
    private List<TransactionDetail> transactionDetails;
}
