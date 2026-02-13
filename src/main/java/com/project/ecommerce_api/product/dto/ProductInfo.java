package com.project.ecommerce_api.product.dto;


import com.project.ecommerce_api.category.domain.Category;
import com.project.ecommerce_api.shared.enums.ProductStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class ProductInfo {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private Integer stockQuantity;
    private Category category;
    private List<String> productImages;
    private ProductStatus status;


}
