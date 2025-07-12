package com.project.ecommerce_api.auth.model.response;

import lombok.Data;

@Data
public class SignupResponse {
    private String first_name;
    private String last_name;
    private String email;
    private Boolean isVerified;
}
