package com.project.ecommerce_api.controllers;

import com.project.ecommerce_api.entities.Wishlist;
import com.project.ecommerce_api.models.auth.response.CustomResponse;
import com.project.ecommerce_api.models.wishlist.WishlistUpdateDto;
import com.project.ecommerce_api.repositories.WishlistRepository;
import com.project.ecommerce_api.services.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wishlist")
public class WishlistController {
    private final WishlistRepository wishlistRepository;
    private final WishlistService wishlistService;

    @GetMapping
    public ResponseEntity<CustomResponse<Wishlist>> getUserWishlist() {
        CustomResponse<Wishlist> response = null;
        try {
            response = wishlistService.getUserWishlist();
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CustomResponse<Wishlist>> addProductToUserWishlist (@RequestBody WishlistUpdateDto wishlistUpdateDto) {
       CustomResponse<Wishlist> response = null;
       try {
           response = wishlistService.addProductToWishlist(wishlistUpdateDto);
           return ResponseEntity.status(response.getStatusCode()).body(response);
       } catch (Exception e) {
           return ResponseEntity.internalServerError().body(response);
       }
    }
}
