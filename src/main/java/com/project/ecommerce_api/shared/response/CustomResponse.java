package com.project.ecommerce_api.shared.response;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class CustomResponse<T> {

    private Boolean success;
    private HttpStatus statusCode;
    private String message;
    @Nullable
    private T data;

    public CustomResponse<?> createCustomResponse(Boolean success,
                                                  HttpStatus statusCode,
                                                  String message,
                                                  T data) {
        return new CustomResponse<>(success, statusCode, message, data);
    }
}
