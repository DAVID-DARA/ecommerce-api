package com.project.ecommerce_api.cart.repository;


import com.project.ecommerce_api.cart.domain.Cart;
import com.project.ecommerce_api.cart.domain.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CartItemRepository extends JpaRepository<CartItem, UUID> {
    List<CartItem> findByCart(Cart cart);

}
