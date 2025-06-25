package com.project.ecommerce_api.controllers;

import com.project.ecommerce_api.models.auth.response.CustomResponse;
import com.project.ecommerce_api.models.cart.AddToCartDto;
import com.project.ecommerce_api.models.cart.CartDetails;
import com.project.ecommerce_api.models.cart.CartUpdateDto;
import com.project.ecommerce_api.services.CartService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CustomResponse<?>> getCart () {
        CustomResponse<?> response = null;
        try {
            response = cartService.getCart();
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CustomResponse<?>> addItemToCart (@RequestBody AddToCartDto request) {
        CustomResponse<?> response = null;
        try {
            response = cartService.addItemToCart(request);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PutMapping("/update-item")
    public ResponseEntity<CustomResponse<CartDetails>> updateCartItem(@RequestBody CartUpdateDto request) {
        CustomResponse<CartDetails> response = null;
        try {
            response = cartService.updateCart(request);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @DeleteMapping("/delete-item/{id}")
    public  ResponseEntity<CustomResponse<?>> deleteCartItem(@PathVariable UUID id) {
        CustomResponse<?> response = null;
        try {
            response = cartService.deleteCartItem(id);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
