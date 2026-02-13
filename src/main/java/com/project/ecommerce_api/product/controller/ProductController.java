package com.project.ecommerce_api.product.controller;


import com.project.ecommerce_api.product.dto.ProductInfo;
import com.project.ecommerce_api.product.service.ProductService;
import com.project.ecommerce_api.shared.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getAllProducts () {
        CustomResponse<List<ProductInfo>> response = null;
        try {
            response = productService.getAllProducts();
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception w) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById (@PathVariable UUID id) {
        CustomResponse<ProductInfo> response = null;
        try {
            response = productService.getProductById(id);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
