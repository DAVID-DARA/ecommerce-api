package com.project.ecommerce_api.auth.controller;

import com.project.ecommerce_api.auth.model.request.LoginUserDto;
import com.project.ecommerce_api.auth.model.request.RegisterUserDto;
import com.project.ecommerce_api.auth.model.request.ResendOtpDto;
import com.project.ecommerce_api.auth.model.request.VerifyUserDto;
import com.project.ecommerce_api.auth.model.response.LoginResponse;
import com.project.ecommerce_api.auth.model.response.SignupResponse;
import com.project.ecommerce_api.auth.model.response.VerificationResponse;
import com.project.ecommerce_api.auth.service.AuthenticationService;
import com.project.ecommerce_api.shared.response.CustomResponse;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup (@RequestBody RegisterUserDto signupRequest) {
        CustomResponse<SignupResponse> response = null;
        try {
            response = authenticationService.signup(signupRequest);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify (@RequestBody VerifyUserDto verificationRequest) {
        CustomResponse<VerificationResponse> response = null;
        try {
            response = authenticationService.verify(verificationRequest);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginUserDto loginRequest) {
        CustomResponse<LoginResponse> response = null;
        try {
            response =  authenticationService.login(loginRequest);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }

    @GetMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestBody ResendOtpDto resendOtpDto) {
        CustomResponse<?> response = null;
        try {
            response = authenticationService.resendOtp(resendOtpDto);
            return ResponseEntity.status(response.getStatusCode()).body(response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(response);
        }
    }
}
