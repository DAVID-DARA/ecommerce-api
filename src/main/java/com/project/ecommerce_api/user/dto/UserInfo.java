package com.project.ecommerce_api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
public class UserInfo {
    private UUID id;
    private String first_name;
    private String last_name;
    private String email;
    private Boolean isVerified;
}
