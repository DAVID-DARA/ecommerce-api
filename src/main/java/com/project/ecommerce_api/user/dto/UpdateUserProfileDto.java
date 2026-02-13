package com.project.ecommerce_api.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateUserProfileDto {
    private String first_name;
    private String last_name;
    private String phone_number;
}
