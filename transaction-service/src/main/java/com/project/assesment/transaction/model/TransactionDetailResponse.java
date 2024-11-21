package com.project.assesment.transaction.model;

import lombok.Data;

@Data
public class TransactionDetailResponse {
    private Integer id;
    private String product;
    private Long price;
    private Integer quantity;
}
