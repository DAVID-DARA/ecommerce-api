package com.project.ecommerce_api.shared.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
public class CustomException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String message;
    private final String errorCode;

    public CustomException(String message) {
        super(message);
        this.message = message;
        this.errorCode = null;
    }

    public CustomException(String message, String errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }
}
