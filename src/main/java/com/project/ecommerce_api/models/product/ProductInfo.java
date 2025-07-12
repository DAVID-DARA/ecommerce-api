package com.project.ecommerce_api.models.product;

import com.project.ecommerce_api.entities.Category;
import com.project.ecommerce_api.entities.ProductImage;
import com.project.ecommerce_api.utilities.ProductStatus;
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
