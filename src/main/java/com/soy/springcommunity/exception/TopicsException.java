package com.soy.springcommunity.exception;

import org.springframework.http.HttpStatus;

public class TopicsException extends BusinessException {

    public TopicsException(HttpStatus status, String message) {
        super(status, message);
    }

    public static class CodeNotFoundException extends TopicsException {
        public CodeNotFoundException(String message) {super(HttpStatus.BAD_REQUEST, message);}
    }


}
