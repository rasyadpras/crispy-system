package com.project.assesment.transaction.entity;

import lombok.Data;

@Data
public class TransactionDetail {
    private Integer id;
    private Integer transactionId;
    private Integer productId;
    private Integer quantity;
}
