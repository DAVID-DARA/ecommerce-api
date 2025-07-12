package com.project.ecommerce_api.product.repository;


import com.project.ecommerce_api.product.domain.Product;
import com.project.ecommerce_api.product.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {

    List<ProductImage> findAllByProduct(Product product);
    int deleteAllByProduct(Product product);
}
