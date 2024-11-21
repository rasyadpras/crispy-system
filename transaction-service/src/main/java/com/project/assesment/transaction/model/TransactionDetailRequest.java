package com.project.assesment.transaction.model;

import lombok.Data;

@Data
public class TransactionDetailRequest {
    private Integer productId;
    private Integer quantity;
}
