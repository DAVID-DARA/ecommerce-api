package com.project.ecommerce_api.models.auth.request;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;
    private String password;
}
