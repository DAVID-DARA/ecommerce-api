package com.project.ecommerce_api.services;

import com.project.ecommerce_api.entities.Category;
import com.project.ecommerce_api.exceptions.CustomException;
import com.project.ecommerce_api.helpers.ResponseUtil;
import com.project.ecommerce_api.models.category.CategoryInfo;
import com.project.ecommerce_api.models.category.CreateCategoryDto;
import com.project.ecommerce_api.models.category.UpdateCategoryDto;
import com.project.ecommerce_api.models.auth.response.CustomResponse;
import com.project.ecommerce_api.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private static  final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public CustomResponse<List<Category>> getAllCategories () {
        CustomResponse<List<Category>> response = new CustomResponse<>();

        try {
            List<Category> categories =  categoryRepository.findAll();

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK);
            response.setMessage("");
            response.setData(categories);
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
            throw new CustomException("Server failed");
        }
        return response;
    }

    public CustomResponse<CategoryInfo> createCategory (CreateCategoryDto categoryDto) {
        CustomResponse<CategoryInfo> response = new CustomResponse<>();
        CategoryInfo categoryInfo;

        Optional<Category> optionalCategory = categoryRepository.findByName(categoryDto.getName());
        if (optionalCategory.isPresent()) {
            return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "category already exists");
        }

        Category newCategory = new Category();
        newCategory.setName(categoryDto.getName());
        newCategory.setDescription(categoryDto.getDescription());

        try {
            Category savedCategory = categoryRepository.save(newCategory);

            categoryInfo = CategoryInfo.builder()
                    .id(savedCategory.getId())
                    .name(savedCategory.getName())
                    .description(savedCategory.getDescription())
                    .build();

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.CREATED);
            response.setMessage("Category Created");
            response.setData(categoryInfo);

            logger.info("Category successfully created: {}", savedCategory.getName());
        } catch (Exception e) {
            logger.error("Error creating category: {}", e.getMessage());
            throw new CustomException("Error creating user");
        }
        return response;
    }

    public CustomResponse<CategoryInfo> updateCategory (UUID categoryId, UpdateCategoryDto updateCategoryDto) {
        CustomResponse<CategoryInfo> response = new CustomResponse<>();
        CategoryInfo categoryInfo;

        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isEmpty()) {
            return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "category not found");
        }
        Category category = optionalCategory.get();

        category.setName(updateCategoryDto.getName());
        category.setDescription(updateCategoryDto.getDescription());

        try {

            categoryRepository.save(category);

            categoryInfo = CategoryInfo.builder()
                    .id(category.getId())
                    .name(category.getName())
                    .description(category.getDescription())
                    .build();

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK);
            response.setMessage("updated category");
            response.setData(categoryInfo);
            logger.info("Category updated successfully");
        } catch (Exception e) {
            logger.error("Error updating category: {}", e.getMessage());
            throw new CustomException("Error updating category");
        }

        return response;
    }

    public CustomResponse<?> deleteCategoryById (UUID id) {
        CustomResponse<?> response = new CustomResponse<>();

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            return  ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "category not found");
        }

        try {
            categoryRepository.deleteById(id);

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK);
            response.setMessage("Category " + "\" " + optionalCategory.get().getName() + " \"" + " deleted");
            logger.info("Category deleted: {}", optionalCategory.get().getName());
        } catch (Exception e) {
            logger.error("Error deleting category: {}", e.getMessage());
            throw new CustomException("Error deleting category: " + optionalCategory.get().getName());
        }

        return response;
    }
}
