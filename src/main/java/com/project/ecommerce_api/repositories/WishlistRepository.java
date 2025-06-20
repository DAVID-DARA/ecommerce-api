package com.project.ecommerce_api.repositories;

import com.project.ecommerce_api.entities.User;
import com.project.ecommerce_api.entities.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, UUID> {

    Optional<Wishlist> findByUser(User user);
}
