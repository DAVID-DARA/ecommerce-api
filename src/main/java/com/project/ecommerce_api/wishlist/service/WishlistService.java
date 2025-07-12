package com.project.ecommerce_api.wishlist.service;


import com.project.ecommerce_api.product.domain.Product;
import com.project.ecommerce_api.product.repository.ProductRepository;
import com.project.ecommerce_api.shared.exceptions.CustomException;
import com.project.ecommerce_api.shared.response.CustomResponse;
import com.project.ecommerce_api.shared.utils.ResponseUtil;
import com.project.ecommerce_api.shared.utils.SecurityUtil;
import com.project.ecommerce_api.user.domain.User;
import com.project.ecommerce_api.wishlist.domain.Wishlist;
import com.project.ecommerce_api.wishlist.dto.WishlistUpdateDto;
import com.project.ecommerce_api.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final SecurityUtil securityUtil;
    private final ProductRepository productRepository;
    private static final Logger logger = LoggerFactory.getLogger(WishlistService.class);

    public CustomResponse<Wishlist> getUserWishlist() {
        CustomResponse<Wishlist> response = new CustomResponse<>();
        try {
            User currentUser = securityUtil.getUser();
            Optional<Wishlist> wishlistOptional = wishlistRepository.findByUser(currentUser);

            if (wishlistOptional.isEmpty()) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "Wishlist not found");
            }
            Wishlist userWishlist = wishlistOptional.get();

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK);
            response.setMessage("Wishlist details");
            response.setData(userWishlist);
        } catch (Exception e) {
            logger.error("Error getting user wishlist");
            throw new CustomException("Error getting user wishlist, getUserWishlist(WishlistService.java)", "500");
        }
        return response;
    }
    public CustomResponse<Wishlist> addProductToWishlist(WishlistUpdateDto wishlistUpdateDto) {
        CustomResponse<Wishlist> response = new CustomResponse<>();
        try {
            User currentUser = securityUtil.getUser();
            System.out.println(currentUser.getId());
            Optional<Wishlist> wishlistOptional = wishlistRepository.findByUser(currentUser);
            if (wishlistOptional.isEmpty()) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "WishlistNotFound");
            }
            Wishlist userWishlist= wishlistOptional.get();
            if (userWishlist.getProducts() == null) {
                userWishlist.setProducts(new HashSet<>());
            }

            Optional<Product> productOptional = productRepository.findById(wishlistUpdateDto.getProductId());
            if (productOptional.isEmpty()) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "Product not found");
            }
            Product productForWishlist = productOptional.get();


            boolean add = userWishlist.getProducts().add(productForWishlist);
            logger.info("save: {}", add);
            wishlistRepository.save(userWishlist);

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK);
            response.setMessage("Wishlist updated");
            response.setData(userWishlist);
        } catch (Exception e) {
            logger.error("Error adding item to Wishlist");
            throw new CustomException("Error adding item to Wishlist, addProductToWishlist(WishlistService.java)", "500");
        }
        return response;
    }
}
