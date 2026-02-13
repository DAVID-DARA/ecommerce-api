package com.project.ecommerce_api.auth.token;

import com.project.ecommerce_api.shared.BaseEntity;
import com.project.ecommerce_api.shared.enums.TokenType;
import com.project.ecommerce_api.user.domain.User;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;


import java.time.LocalDateTime;

import java.util.UUID;

@Getter
@Setter
@ToString(exclude = "user")
@Entity
@Table(name = "tokens")
public class Token extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false, updatable = false)
    private User user;

    @Column(nullable = false, updatable = false)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TokenType type;

    @Column(name = "expired_at", nullable = false, updatable = false)
    private LocalDateTime expiredAt;

    @Column(name = "is_used", nullable = false)
    private Boolean isUsed = false;
}
