package com.project.assesment.product.model;

import lombok.Data;

@Data
public class ProductRequest {
    private String product;
    private Long price;
    private Integer stock;
}
