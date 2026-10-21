package com.tanishmohanta.synapse.user_service.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;

@Data
public class APIError {
    private String errorMessage;
    private HttpStatus status;
    private Instant timestamp;
    public APIError() {
        this.timestamp = Instant.now();
    }

    public APIError(String errorMessage, HttpStatus status) {
        this();
        this.errorMessage = errorMessage;
        this.status = status;
    }
}
