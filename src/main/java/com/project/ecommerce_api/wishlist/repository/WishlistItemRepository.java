package com.project.ecommerce_api.wishlist.repository;

import com.project.ecommerce_api.wishlist.domain.Wishlist;
import com.project.ecommerce_api.wishlist.domain.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, UUID> {
    List<WishlistItem> findByWishlist(Wishlist wishlist);
}
