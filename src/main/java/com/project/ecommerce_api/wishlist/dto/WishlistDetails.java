package com.project.ecommerce_api.wishlist.dto;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class WishlistDetails {

    private UUID wishlistId;
    private UUID userId;
    private List<WishlistItemResponse> wishlistItems;
}
