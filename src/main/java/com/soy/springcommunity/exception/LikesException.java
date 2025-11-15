package com.soy.springcommunity.exception;

import org.springframework.http.HttpStatus;

public class LikesException extends BusinessException {

    public LikesException(HttpStatus status, String message) {
        super(status, message);
    }

    public static class NotFoundException extends BusinessException {
        public NotFoundException(String message) {
            super(HttpStatus.NOT_FOUND, message);
        }
    }

    public static class AlreadyLikedException extends BusinessException {
        public AlreadyLikedException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
    }

    public static class AlreadyDislikedException extends BusinessException {
        public AlreadyDislikedException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
    }
}
