package com.project.ecommerce_api.cart.repository;


import com.project.ecommerce_api.cart.domain.Cart;
import com.project.ecommerce_api.user.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByUser(User user);

    @Query("SELECT c FROM Cart c WHERE c.user = :user")
    @EntityGraph(attributePaths = {"items"})
    Optional<Cart> findByUserWithItems(@Param("user") User user);
}
