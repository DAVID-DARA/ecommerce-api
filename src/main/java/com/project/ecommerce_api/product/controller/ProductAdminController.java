package com.project.ecommerce_api.product.controller;


import com.project.ecommerce_api.product.domain.Product;
import com.project.ecommerce_api.product.dto.CreateProductDto;
import com.project.ecommerce_api.product.dto.ProductInfo;
import com.project.ecommerce_api.product.service.ProductService;
import com.project.ecommerce_api.shared.response.CustomResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/products")
public class ProductAdminController {

    private final ProductService productService;


    @PostMapping
    public ResponseEntity<?> createProduct (@RequestParam("imageFile") MultipartFile imageFile,
                                            @RequestParam("name") String name,
                                            @RequestParam("description") String description,
                                            @RequestParam("price") BigDecimal price,
                                            @RequestParam("stockQuantity") Integer stockQuantity,
                                            @RequestParam("categoryId") UUID categoryId,
                                            @RequestParam("altText") String altText)
    {
        CustomResponse<ProductInfo> response = null;

        CreateProductDto productDto = new CreateProductDto();
        productDto.setName(name);
        productDto.setDescription(description);
        productDto.setPrice(price);
        productDto.setStockQuantity(stockQuantity);
        productDto.setCategoryId(categoryId);
        productDto.setAltText(altText);
        productDto.setFile(imageFile);

        try {
            response = productService.addProduct(productDto);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PatchMapping("/update")
    public ResponseEntity<?> updateProduct (@PathVariable UUID productId ,@RequestBody Product updateProductDto) {
        CustomResponse<ProductInfo> response = null;
        try {
            response = productService.updateProduct(productId, updateProductDto);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct (@PathVariable UUID id) {
        CustomResponse<?> response = null;
        try {
            response = productService.deleteProduct(id);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
