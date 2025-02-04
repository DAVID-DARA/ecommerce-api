package com.project.ecommerce_api.models.auth.response;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class CustomResponse<T> {

    private Boolean success;
    private HttpStatus statusCode;
    private String message;
    private T data;
}
