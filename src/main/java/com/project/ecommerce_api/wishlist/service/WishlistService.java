package com.project.ecommerce_api.wishlist.service;


import com.project.ecommerce_api.product.domain.Product;
import com.project.ecommerce_api.product.repository.ProductRepository;
import com.project.ecommerce_api.shared.exceptions.CustomException;
import com.project.ecommerce_api.shared.response.CustomResponse;
import com.project.ecommerce_api.shared.utils.ResponseUtil;
import com.project.ecommerce_api.shared.utils.SecurityUtil;
import com.project.ecommerce_api.user.domain.User;
import com.project.ecommerce_api.wishlist.domain.Wishlist;
import com.project.ecommerce_api.wishlist.domain.WishlistItem;
import com.project.ecommerce_api.wishlist.dto.WishlistDetails;
import com.project.ecommerce_api.wishlist.dto.WishlistItemResponse;
import com.project.ecommerce_api.wishlist.dto.WishlistUpdateDto;
import com.project.ecommerce_api.wishlist.repository.WishlistItemRepository;
import com.project.ecommerce_api.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final WishlistItemRepository wishlistItemRepository;
    private final SecurityUtil securityUtil;
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(WishlistService.class);

    public CustomResponse<WishlistDetails> getUserWishlist() {
        CustomResponse<WishlistDetails> response = new CustomResponse<>();
        try {
            User currentUser = securityUtil.getUser();
            Optional<Wishlist> wishlistOptional = wishlistRepository.findByUser(currentUser);

            if (wishlistOptional.isEmpty()) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "Wishlist not found");
            }
            Wishlist userWishlist = wishlistOptional.get();

            WishlistDetails wishlistDetails = getWishlistDetails(userWishlist);
            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK);
            response.setMessage("Wishlist details");
            response.setData(wishlistDetails);
        } catch (Exception e) {
            logger.error("Error getting user wishlist: {}", e.getMessage());
            throw new CustomException("Error getting user wishlist, getUserWishlist(WishlistService.java)", "500");
        }
        return response;
    }

    public CustomResponse<?> addProductToWishlist(WishlistUpdateDto wishlistUpdateDto) {
        CustomResponse<?> response = new CustomResponse<>();
        try {
            User currentUser = securityUtil.getUser();
            Optional<Wishlist> wishlistOptional = wishlistRepository.findByUser(currentUser);
            if (wishlistOptional.isEmpty()) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "WishlistNotFound");
            }
            Wishlist userWishlist= wishlistOptional.get();

            Optional<Product> productOptional = productRepository.findById(wishlistUpdateDto.getProductId());
            if (productOptional.isEmpty()) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "Product not found");
            }
            Product productForWishlist = productOptional.get();
            WishlistItem wishlistItem = new WishlistItem();
            wishlistItem.setWishlist(userWishlist);
            wishlistItem.setProduct(productForWishlist);
            wishlistItem.setPrice(productForWishlist.getPrice());

            wishlistItemRepository.save(wishlistItem);

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK);
            response.setMessage("Wishlist updated");
            response.setData(null);
        } catch (Exception e) {
            logger.error("Error adding item to Wishlist");
            throw new CustomException("Error adding item to Wishlist, addProductToWishlist(WishlistService.java)", "500");
        }
        return response;
    }

    private List<WishlistItemResponse> getWishlistItems(Wishlist wishlist) {
        List<WishlistItem> wishlistItems = wishlistItemRepository.findByWishlist(wishlist);
        return wishlistItems.stream()
                .map(item -> new WishlistItemResponse(item.getId(), item.getProduct().getId(), item.getProduct().getPrice()))
                .collect(Collectors.toList());
    }

    private WishlistDetails getWishlistDetails (Wishlist wishlist) {
        return WishlistDetails.builder()
                .wishlistId(wishlist.getId())
                .userId(wishlist.getUser().getId())
                .wishlistItems(getWishlistItems(wishlist))
                .build();
    }
}
