package com.project.ecommerce_api.shared.utils;

import com.project.ecommerce_api.shared.response.CustomResponse;
import org.springframework.http.HttpStatus;

public class ResponseUtil {
    public static <T> CustomResponse<T> createErrorResponse (
            CustomResponse<T> response,
            HttpStatus status,
            String message
    ) {
        response.setSuccess(false);
        response.setStatusCode(status);
        response.setMessage(message);
        response.setData(null);

        return response;
    }
}
