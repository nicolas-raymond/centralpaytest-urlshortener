package com.centralpaytest.urlshortener.exception;

import org.springframework.http.HttpStatus;

public class RestException extends Exception {
    HttpStatus httpCode;

    public RestException(HttpStatus code, String message) {
        super(message);
        httpCode = code;
    }
}
