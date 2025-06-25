package com.project.ecommerce_api.models.auth.response;

import com.project.ecommerce_api.models.JwtAuthenticationResponse;
import com.project.ecommerce_api.models.UserInfo;
import lombok.Data;

@Data
public class LoginResponse {
    private JwtAuthenticationResponse authInfo;
    private UserInfo userInfo;
}