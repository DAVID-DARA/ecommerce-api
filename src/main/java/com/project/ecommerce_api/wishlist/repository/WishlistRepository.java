package com.project.ecommerce_api.wishlist.repository;


import com.project.ecommerce_api.user.domain.User;
import com.project.ecommerce_api.wishlist.domain.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, UUID> {

    Optional<Wishlist> findByUser(User user);
}
