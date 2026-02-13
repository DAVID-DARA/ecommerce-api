package com.project.ecommerce_api.wishlist.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.project.ecommerce_api.product.domain.Product;
import com.project.ecommerce_api.shared.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@Entity
@Table(name = "wishlist_items")
public class WishlistItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wishlist_id", referencedColumnName = "id", updatable = false, nullable = false)
    @JsonBackReference
    private Wishlist wishlist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", referencedColumnName = "id", nullable = false)
    private Product product;

    @Column(nullable = false)
    private BigDecimal price;
}
