package com.project.ecommerce_api.controllers;

import com.project.ecommerce_api.models.user.UpdateUserProfileDto;
import com.project.ecommerce_api.models.UserInfo;
import com.project.ecommerce_api.models.auth.response.CustomResponse;
import com.project.ecommerce_api.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<CustomResponse<UserInfo>> getUserById (@PathVariable UUID id) {
        CustomResponse<UserInfo> response = null;
        try {
            response = userService.getUserDetails(id);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<UserInfo>> updateUserById (@PathVariable UUID id, @RequestBody UpdateUserProfileDto profileDto) {
        CustomResponse<UserInfo> response = null;
        try {
            response = userService.updateUser(id, profileDto);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteUserById (@PathVariable UUID id) {
//        CustomResponse<?> response = null;
//        try {
//            response = userService.deleteUser(id);
//            return ResponseEntity.status(response.getStatusCode()).body(response);
//        } catch (Exception e) {
//            return ResponseEntity.internalServerError().body(response);
//        }
//    }
}
