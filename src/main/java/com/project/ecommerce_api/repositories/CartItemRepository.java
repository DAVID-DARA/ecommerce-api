package com.project.ecommerce_api.repositories;

import com.project.ecommerce_api.entities.Cart;
import com.project.ecommerce_api.entities.CartItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItem> findByCart(Cart cart);

}
