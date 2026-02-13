package com.project.ecommerce_api.wishlist.controller;


import com.project.ecommerce_api.shared.response.CustomResponse;
import com.project.ecommerce_api.wishlist.dto.WishlistDetails;
import com.project.ecommerce_api.wishlist.dto.WishlistUpdateDto;
import com.project.ecommerce_api.wishlist.repository.WishlistRepository;
import com.project.ecommerce_api.wishlist.service.WishlistService;
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
    public ResponseEntity<CustomResponse<WishlistDetails>> getUserWishlist() {
        CustomResponse<WishlistDetails> response = null;
        try {
            response = wishlistService.getUserWishlist();
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<CustomResponse<?>> addProductToUserWishlist (@RequestBody WishlistUpdateDto wishlistUpdateDto) {
       CustomResponse<?> response = null;
       try {
           response = wishlistService.addProductToWishlist(wishlistUpdateDto);
           return ResponseEntity.status(response.getStatusCode()).body(response);
       } catch (Exception e) {
           return ResponseEntity.internalServerError().body(response);
       }
    }
}
