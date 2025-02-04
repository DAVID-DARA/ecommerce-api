package com.project.ecommerce_api.services;

import com.project.ecommerce_api.entities.User;
import com.project.ecommerce_api.exceptions.CustomException;
import com.project.ecommerce_api.helpers.ResponseUtil;
import com.project.ecommerce_api.models.user.UpdateUserProfileDto;
import com.project.ecommerce_api.models.UserInfo;
import com.project.ecommerce_api.models.auth.response.CustomResponse;
import com.project.ecommerce_api.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final static Logger logger = LoggerFactory.getLogger(UserService.class);

    public CustomResponse<UserInfo> getUserDetails (UUID id) {
        CustomResponse<UserInfo> userResponse = new CustomResponse<>();

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseUtil.createErrorResponse(userResponse, HttpStatus.NOT_FOUND, "user not found");
        }
        User user = userOptional.get();

        UserInfo userInfo = UserInfo.builder()
                .id(user.getId())
                .first_name(user.getFirstName())
                .last_name(user.getLastName())
                .email(user.getEmail())
                .isVerified(user.getIsVerified())
                .build();
        userResponse.setSuccess(true);
        userResponse.setStatusCode(HttpStatus.OK);
        userResponse.setMessage("User Details");
        userResponse.setData(userInfo);

        return userResponse;
    }

    public CustomResponse<UserInfo> updateUser (UUID id, UpdateUserProfileDto updateInfo) {
        CustomResponse<UserInfo> response = new CustomResponse<>();
        UserInfo updatedInfo;

        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            return ResponseUtil.createErrorResponse(response, HttpStatus.NOT_FOUND, "User not found");
        }
        User requiredUser = userOptional.get();

        requiredUser.setFirstName(updateInfo.getFirst_name());
        requiredUser.setLastName(updateInfo.getLast_name());
        requiredUser.setPhoneNumber(updateInfo.getPhone_number());
        requiredUser.setUpdatedAt(LocalDateTime.now());

        try {
            userRepository.save(requiredUser);

            updatedInfo = UserInfo.builder()
                .id(requiredUser.getId())
                .first_name(requiredUser.getFirstName())
                .last_name(requiredUser.getLastName())
                .email(requiredUser.getEmail())
                .isVerified(requiredUser.getIsVerified())
                .build();

            response.setSuccess(true);
            response.setStatusCode(HttpStatus.OK);
            response.setMessage("update successful");
            response.setData(updatedInfo);

            logger.info("Successfully updated user: {}", requiredUser.getId());
        } catch (Exception e) {
            logger.error("Error updating user: ", e);
            throw new CustomException("Error updating user");
        }
        return response;
    }

//    public CustomResponse<?> deleteUser (UUID id) {
//        CustomResponse<?> response = new CustomResponse<>();
//
//        Optional<User> userOptional = userRepository.findByUserId(id);
//        if (userOptional.isEmpty()) {
//            return createErrorResponse(response, HttpStatus.CONFLICT, "User not found");
//        }
//
//        try {
//            tokenRepository.deleteAllByUser(userOptional.get());
//            userRepository.deleteById(userOptional.get().getUserId());
//
//            response.setSuccess(true);
//            response.setStatusCode(HttpStatus.OK);
//            response.setMessage("User successfully deleted: " + userOptional.get().getUserId());
//        } catch (Exception e) {
//            logger.error("Error deleting user: ", e);
//            throw new CustomException("Error deleting user");
//        }
//        return response;
//    }

}
