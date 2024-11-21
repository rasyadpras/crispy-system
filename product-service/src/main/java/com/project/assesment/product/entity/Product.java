package com.project.assesment.product.entity;

import lombok.Data;

@Data
public class Product {
    private Integer id;
    private String product;
    private Long price;
    private Integer stock;
}
