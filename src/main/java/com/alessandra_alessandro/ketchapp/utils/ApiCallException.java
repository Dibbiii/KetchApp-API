package com.alessandra_alessandro.ketchapp.utils;

import com.alessandra_alessandro.ketchapp.models.ErrorResponse;
import lombok.Getter;

@Getter
public class ApiCallException extends RuntimeException {
    private final ErrorResponse errorResponse;
    public ApiCallException(ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.errorResponse = errorResponse;
    }
}

