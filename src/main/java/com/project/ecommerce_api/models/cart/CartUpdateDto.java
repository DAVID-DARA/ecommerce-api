package com.project.ecommerce_api.models.cart;

import lombok.Data;

import java.util.UUID;

@Data
public class CartUpdateDto {

    private UUID cartItemId;

    private Integer quantity;
}
