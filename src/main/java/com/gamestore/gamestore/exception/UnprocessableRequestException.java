package com.gamestore.gamestore.exception;

public class UnprocessableRequestException extends RuntimeException {

    public UnprocessableRequestException(String message) {
        super(message);
    }

    public UnprocessableRequestException() {
        super();
    }

}