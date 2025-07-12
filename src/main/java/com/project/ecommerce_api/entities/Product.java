package com.project.ecommerce_api.entities;

import com.project.ecommerce_api.utilities.ProductStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Table(name = "product")
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(name = "stock_quantity", nullable = false)
    private Integer stockQuantity;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @CreationTimestamp
    @Column(name = "date_added", nullable = false)
    private LocalDateTime dateAdded;

    @Enumerated(EnumType.STRING)
    private ProductStatus status;

    @OneToMany(
            mappedBy = "product",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ProductImage> productImages = new ArrayList<>();

    public void addProductImage(ProductImage image) {
        productImages.add(image);
        image.setProduct(this);
    }

    public void removeProductImage(ProductImage image) {
        productImages.remove(image);
        image.setProduct(null);
    }
}
