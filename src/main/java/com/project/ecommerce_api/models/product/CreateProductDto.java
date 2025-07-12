package com.project.ecommerce_api.models.product;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class CreateProductDto {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private UUID categoryId;
    private String altText;
    private MultipartFile file;

}
