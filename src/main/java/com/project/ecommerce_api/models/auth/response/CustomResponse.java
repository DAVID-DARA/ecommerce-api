package com.project.ecommerce_api.models.auth.response;

import jakarta.annotation.Nullable;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
public class CustomResponse<T> {

    private Boolean success;
    private HttpStatus statusCode;
    private String message;
    @Nullable
    private T data;

//    public CustomResponse<?> createCustomResponse(CustomResponse<?> response, Boolean success,
//                                                  HttpStatus statusCode, String message, T data) {
//        response = new CustomResponse<>(success, statusCode, message, data);
//        return response;
//    }
}
