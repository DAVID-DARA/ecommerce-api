package com.project.ecommerce_api.controllers;

import com.project.ecommerce_api.services.CategoryService;
import com.project.ecommerce_api.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final CategoryService categoryService;
    private final ProductService productService;





}
