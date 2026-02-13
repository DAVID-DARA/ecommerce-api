package com.project.ecommerce_api.auth.model.request;

import lombok.Data;

@Data
public class LoginUserDto {
    private String email;
    private String password;
}
