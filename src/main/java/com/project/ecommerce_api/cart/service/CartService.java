package com.project.ecommerce_api.cart.service;



import com.project.ecommerce_api.cart.domain.Cart;
import com.project.ecommerce_api.cart.domain.CartItem;
import com.project.ecommerce_api.cart.dto.AddToCartDto;
import com.project.ecommerce_api.cart.dto.CartDetails;
import com.project.ecommerce_api.cart.dto.CartItemResponse;
import com.project.ecommerce_api.cart.dto.CartUpdateDto;
import com.project.ecommerce_api.cart.repository.CartItemRepository;
import com.project.ecommerce_api.cart.repository.CartRepository;
import com.project.ecommerce_api.product.domain.Product;
import com.project.ecommerce_api.product.repository.ProductRepository;
import com.project.ecommerce_api.shared.exceptions.CustomException;
import com.project.ecommerce_api.shared.response.CustomResponse;
import com.project.ecommerce_api.shared.utils.ResponseUtil;
import com.project.ecommerce_api.shared.utils.SecurityUtil;
import com.project.ecommerce_api.user.domain.User;
import com.project.ecommerce_api.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final SecurityUtil securityUtil;


    private final static Logger logger = LoggerFactory.getLogger(CartService.class);

    public CustomResponse<CartDetails> getCart() {
        try {
            CustomResponse<CartDetails> response = new CustomResponse<>();
            Optional<User> userOptional = userRepository.findByEmail(securityUtil.getUserEmail());
            if (userOptional.isEmpty()) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "User not found");
            }

            User requiredUser = userOptional.get();
            Optional<Cart> cartOptional = cartRepository.findByUserWithItems(requiredUser);
            if (cartOptional.isEmpty()) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "Cart doesn't exist");
            }
            Cart userCart = cartOptional.get();

            CartDetails cartDetails = getCartDetails(userCart);

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.FOUND);
            response.setMessage("Cart Details");
            response.setData(cartDetails);

            return response;
        } catch (Exception e) {
            logger.error("500: Internal Server Error");
            throw new CustomException("Internal Server Error");
        }
    }

    public CustomResponse<?> addItemToCart(AddToCartDto request) {
        CustomResponse<?> response = new CustomResponse<>();
        try {
            Optional<User> userOptional = userRepository.findByEmail(securityUtil.getUserEmail());
            User currentUser = userOptional.get();

            Optional<Product> productOptional = productRepository.findById(request.getProductId());
            if (productOptional.isEmpty()) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "Product not found");
            }
            Product cartProduct = productOptional.get();

            Cart userCart = getUserCart(currentUser);

            CartItem cartItem = new CartItem();

            cartItem.setCart(userCart);
            cartItem.setProduct(cartProduct);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(cartProduct.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity())));

            cartItemRepository.save(cartItem);
            response.setSuccess(true);
            response.setMessage(cartProduct.getId() + " added to cart.");
            response.setStatusCode(HttpStatus.OK);
            response.setData(null);
        } catch (Exception e) {
            logger.error("Internal Server Error");
            throw new CustomException("Internal Server Error");
        }

        return response;
    }

    public CustomResponse<CartDetails> updateCart(CartUpdateDto request) {
        CustomResponse<CartDetails> response = new CustomResponse<>();
        try {
            User currentUser = securityUtil.getUser();
            if (currentUser == null) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "User not found");
            }

            Cart currentUserCart = getUserCart(currentUser);
            if(currentUserCart == null) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "Cart not found");
            }

            Optional<CartItem> cartItemOptional = cartItemRepository.findById(request.getCartItemId());
            if (cartItemOptional.isEmpty()) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "Cart Item not found");
            }

            CartItem cartItemForUpdate = cartItemOptional.get();
            cartItemForUpdate.setQuantity(request.getQuantity());

            cartItemRepository.save(cartItemForUpdate);

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK);
            response.setMessage("Cart updated successfully");
            response.setData(getCartDetails(currentUserCart));

            logger.info("Cart Item successfully updated: {}", cartItemForUpdate.getId());

        } catch (Exception e) {
            logger.error("Error updating cart, updateCart(CartService.java)");
            throw new CustomException(e.getLocalizedMessage(), "500");
        }
        return response;
    }


    public CustomResponse<CartDetails> deleteCartItem(UUID id) {
        CustomResponse<CartDetails> response = new CustomResponse<>();
        try {
            User currentUser = securityUtil.getUser();
            if (currentUser == null) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "User not found");
            }

            Cart currentUserCart = getUserCart(currentUser);
            if(currentUserCart == null) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "Cart not found");
            }

            Optional<CartItem> cartItemOptional = cartItemRepository.findById(id);
            if (cartItemOptional.isEmpty()) {
                return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "Cart Item not found");
            }

            cartItemRepository.delete(cartItemOptional.get());
            logger.info("CartItem successfully removed: {}", cartItemOptional.get().getId());

            response.setSuccess(true);
            response.setMessage("Cart Details");
            response.setStatusCode(HttpStatus.OK);
            response.setData(getCartDetails(currentUserCart));

        } catch (Exception e) {
            logger.error("Error deleting cartItem, deleteCartItem(CartService.java)");
            throw new CustomException("InternalServerError", "500");
        }

        return response;
    }



    private Cart getUserCart (User user) {
        Optional<Cart> cartOptional = cartRepository.findByUser(user);
        return cartOptional.orElse(null);
    }

    private List<CartItemResponse> getCartItems(Cart cart) {
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        return cartItems.stream()
                .map(item -> new CartItemResponse(item.getId(), item.getProduct().getId(), item.getQuantity()))
                .collect(Collectors.toList());
    }

    private CartItemResponse getCartItemResponse(CartItem cartItem) {
        return CartItemResponse.builder()
                .id(cartItem.getId())
                .productId(cartItem.getProduct().getId())
                .quantity(cartItem.getQuantity())
                .build();
    }

    private CartDetails getCartDetails(Cart cart) {
        return CartDetails.builder()
                .cartId(cart.getId())
                .userId(cart.getUser().getId())
                .cartItems(getCartItems(cart))
                .build();
    }
}
