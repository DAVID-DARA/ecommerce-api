package com.project.ecommerce_api.auth.model.request;


import com.project.ecommerce_api.shared.enums.TokenType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResendOtpDto {
    @Email
    @NotBlank(message = "This field is required")
    private String userEmail;

    @NotBlank(message = "This field is required")
    private TokenType tokenType;
}
