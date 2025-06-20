package com.project.ecommerce_api.repositories;

import com.project.ecommerce_api.entities.Token;
import com.project.ecommerce_api.entities.User;
import com.project.ecommerce_api.utilities.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    Optional<Token> findByUser(User user);

    List<Token> findByUserAndType(User user, TokenType type);

    Optional<Token> findFirstByUserIdAndTypeOrderByCreatedAtDesc(UUID userId, TokenType type);

}
