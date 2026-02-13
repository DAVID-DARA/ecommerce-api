package com.project.ecommerce_api.shared.utils;

import com.project.ecommerce_api.shared.exceptions.CustomException;
import com.project.ecommerce_api.user.domain.User;
import com.project.ecommerce_api.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class SecurityUtil {

    private final UserRepository userRepository;

    public User getUser() {
        try {
            String userEmail = getUserEmail();
            Optional<User> userOptional = userRepository.findByEmail(userEmail);
            if (userOptional.isEmpty()) {
                throw new CustomException("User not Found", "500");
            }
            return userOptional.get();
        } catch (Exception e) {
            throw new CustomException("Internal Server Error", "500");
        }

    }

    public UUID getUserId() {
        return getUser().getId();
    }

    public String getUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        }
        return "Error security util";
    }
}
