package com.soy.springcommunity.exception;

import org.springframework.http.HttpStatus;

public class CommentsException extends BusinessException {
    public CommentsException(HttpStatus status, String message) {
        super(status, message);
    }

    public static class CommentsNotFoundException extends BusinessException {
        public CommentsNotFoundException(String message) {
            super(HttpStatus.NOT_FOUND, message);
        }
    }

    public static class CommentsNotMatchPostException extends BusinessException {
        public CommentsNotMatchPostException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
    }

    public static class CommentsUnauthorizedException extends BusinessException {
        public CommentsUnauthorizedException(String message) {
            super(HttpStatus.FORBIDDEN, message);
        }
    }
}

