package com.soy.springcommunity.exception;


import org.springframework.http.HttpStatus;

public abstract class BusinessException extends RuntimeException {
    private final HttpStatus status;
    private final String message;
    public BusinessException(HttpStatus status, String message) {
        super();
        this.message = message;
        this.status = status;
    }
    public HttpStatus getStatus() {return status;}
    public String getMessage() {return message;}
}
