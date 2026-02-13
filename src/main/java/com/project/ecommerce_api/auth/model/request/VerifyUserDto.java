package com.project.ecommerce_api.auth.model.request;

import lombok.Data;

@Data
public class VerifyUserDto {
    private String email;
    private String token;
}
