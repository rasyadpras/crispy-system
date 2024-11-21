package com.project.assesment.product.model;

import lombok.Data;

@Data
public class ProductResponse {
    private Integer id;
    private String product;
    private Long price;
    private Integer stock;
}
