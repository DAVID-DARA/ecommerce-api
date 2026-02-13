package com.project.ecommerce_api.cart.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CartDetails {

    private UUID cartId;

    private UUID userId;

    private List<CartItemResponse> cartItems;
}
