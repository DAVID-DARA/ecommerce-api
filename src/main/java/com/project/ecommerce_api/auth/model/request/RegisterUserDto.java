package com.project.ecommerce_api.auth.model.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
public class RegisterUserDto {
    @Size(min = 3)
    @NotBlank(message = "This field is required")
    private String first_name;

    @Size(min = 3)
    @NotBlank(message = "This field is required")
    private String last_name;

    @Size(min = 11, max = 11)
    @NotBlank(message = "This field is required")
    private String phone_number;

    @Email
    @NotBlank(message = "This field is required")
    private String email;

    @Size(min = 8)
    @NotBlank(message = "This field is required")
    private String password;

}
