package com.project.ecommerce_api.auth.model.response;

import com.project.ecommerce_api.auth.model.JwtAuthenticationResponse;
import com.project.ecommerce_api.user.dto.UserInfo;

import lombok.Data;

@Data
public class VerificationResponse {
    private JwtAuthenticationResponse authInfo;
    private UserInfo user;
}
