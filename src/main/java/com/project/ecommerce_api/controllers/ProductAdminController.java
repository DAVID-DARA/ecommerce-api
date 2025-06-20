package com.project.ecommerce_api.controllers;

import com.project.ecommerce_api.entities.Product;
import com.project.ecommerce_api.models.auth.response.CustomResponse;
import com.project.ecommerce_api.models.product.CreateProductDto;
import com.project.ecommerce_api.models.product.ProductInfo;
import com.project.ecommerce_api.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
                                            @RequestParam("price") Double price,
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
