package com.project.ecommerce_api.controllers;

import com.project.ecommerce_api.entities.Product;
import com.project.ecommerce_api.models.category.CategoryInfo;
import com.project.ecommerce_api.models.category.CreateCategoryDto;
import com.project.ecommerce_api.models.category.UpdateCategoryDto;
import com.project.ecommerce_api.models.auth.response.CustomResponse;
import com.project.ecommerce_api.models.product.CreateProductDto;
import com.project.ecommerce_api.models.product.ProductInfo;
import com.project.ecommerce_api.services.CategoryService;
import com.project.ecommerce_api.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final CategoryService categoryService;
    private final ProductService productService;

    //CATEGORIES ADMIN ENDPOINT
    @PostMapping("/categories")
    public ResponseEntity<?> createCategory (@RequestBody CreateCategoryDto categoryDto) {
        CustomResponse<CategoryInfo> response = null;
        try {
            response = categoryService.createCategory(categoryDto);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/categories/{id}")
    public ResponseEntity<?> updateCategory (@PathVariable UUID id,
                                             @RequestBody UpdateCategoryDto updateCategoryDto
    ) {
        CustomResponse<CategoryInfo> response = null;
        try {
            response = categoryService.updateCategory(id, updateCategoryDto);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/categories/{id}")
    public ResponseEntity<?> deleteCategory (@PathVariable UUID id) {
        CustomResponse<?> response = null;
        try {
            response = categoryService.deleteCategoryById(id);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }



    // PRODUCTS ADMIN ENDPOINTS
    @GetMapping("/products")
    public ResponseEntity<?> getAllProducts () {
        CustomResponse<List<ProductInfo>> response = null;
        try {
            response = productService.getAllProducts();
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception w) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/products/{id}")
    public ResponseEntity<?> getProductById (@PathVariable UUID id) {
        CustomResponse<ProductInfo> response = null;
        try {
            response = productService.getProductById(id);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/products")
    public ResponseEntity<?> createProduct (@RequestParam("imageFile")MultipartFile imageFile,
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

    @PatchMapping("/products/update")
    public ResponseEntity<?> updateProduct (@PathVariable UUID productId ,@RequestBody Product updateProductDto) {
        CustomResponse<ProductInfo> response = null;

        try {
            response = productService.updateProduct(productId, updateProductDto);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/products/{id}")
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
