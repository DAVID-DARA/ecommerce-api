package com.project.ecommerce_api.repositories;

import com.project.ecommerce_api.entities.Token;
import com.project.ecommerce_api.entities.User;
import com.project.ecommerce_api.utilities.TokenType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    Optional<Token> findByUser(User user);

    @Modifying
    @Query("UPDATE Token t SET t.isUsed = true WHERE t.user.id = :userId AND t.type = :type AND t.isUsed = false")
    void invalidateTokens(@Param("userId") Long userId, @Param("type") TokenType type);

    @Query("SELECT t FROM Token t WHERE t.user.id = :userId AND t.type = :type AND t.isUsed = false AND t.expiredAt > :now ORDER BY t.createdAt DESC")
    Optional<Token> findFirstByUser_IdAndTypeAndIsUsedFalseAndExpiredAtAfter(@Param("userId") UUID userId, @Param("type") TokenType type, @Param("now") LocalDateTime now);
}
