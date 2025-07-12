package com.project.ecommerce_api.category.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CategoryInfo {
    private UUID id;
    private String name;
    private String description;
}
