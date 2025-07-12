package com.project.ecommerce_api.user.controller;


import com.project.ecommerce_api.shared.response.CustomResponse;
import com.project.ecommerce_api.user.dto.UpdateUserProfileDto;
import com.project.ecommerce_api.user.dto.UserInfo;
import com.project.ecommerce_api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<CustomResponse<UserInfo>> getUser() {
        CustomResponse<UserInfo> response = null;
        try {
            response = userService.getUserDetails();
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CustomResponse<UserInfo>> updateUserById (@RequestBody UpdateUserProfileDto profileDto) {
        CustomResponse<UserInfo> response = null;
        try {
            response = userService.updateUser(profileDto);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

}
