package com.project.ecommerce_api.controllers;

import com.project.ecommerce_api.models.auth.response.CustomResponse;
import com.project.ecommerce_api.models.category.CategoryInfo;
import com.project.ecommerce_api.models.category.CreateCategoryDto;
import com.project.ecommerce_api.models.category.UpdateCategoryDto;
import com.project.ecommerce_api.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/categories")
public class CategoryAdminController {

    private final CategoryService categoryService;

    //CATEGORIES ADMIN ENDPOINT
    @PostMapping
    public ResponseEntity<?> createCategory (@RequestBody CreateCategoryDto categoryDto) {
        CustomResponse<CategoryInfo> response = null;
        try {
            response = categoryService.createCategory(categoryDto);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/{id}")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCategory (@PathVariable UUID id) {
        CustomResponse<?> response = null;
        try {
            response = categoryService.deleteCategoryById(id);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
