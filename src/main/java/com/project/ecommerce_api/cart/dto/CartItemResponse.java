package com.project.ecommerce_api.cart.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@ToString
@AllArgsConstructor
@Builder
public class CartItemResponse {

    private UUID id;
    private UUID productId;
    private Integer quantity;
}
