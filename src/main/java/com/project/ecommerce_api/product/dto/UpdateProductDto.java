package com.project.ecommerce_api.product.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateProductDto {
    private String name;
    private String description;
    private Double price;
    private Integer stockQuantity;
    private UUID categoryId;
    private String altText;
}
