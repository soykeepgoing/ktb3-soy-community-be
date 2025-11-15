package com.soy.springcommunity.exception;

import com.soy.springcommunity.exception.UsersException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsersException.class)
    public ResponseEntity<?> handleUsersException(UsersException e) {
        return ResponseEntity
                .status(e.getStatus())
                .body(new ErrorResponse(e.getMessage(), e.getStatus().value()));
    }

    // 예외 응답 DTO
    static class ErrorResponse {
        private final String message;
        private final int status;

        public ErrorResponse(String message, int status) {
            this.message = message;
            this.status = status;
        }

        public String getMessage() { return message; }
        public int getStatus() { return status; }
    }
}
