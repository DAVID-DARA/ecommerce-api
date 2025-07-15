package com.project.ecommerce_api.wishlist.domain;


import com.project.ecommerce_api.shared.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "wishlist_items")
public class WishlistItem extends BaseEntity {

}
