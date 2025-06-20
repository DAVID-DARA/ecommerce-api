package com.project.ecommerce_api.models.wishlist;

import lombok.Data;

import java.util.UUID;

@Data
public class WishlistUpdateDto {

    private UUID productId;
}
