package com.soy.springcommunity.exception;

import org.springframework.http.HttpStatus;

public class UsersException extends BusinessException {

    public UsersException(HttpStatus status, String message) {
        super(status, message);
    }

    public static class UsersNotFoundException extends UsersException {
        public UsersNotFoundException(String message) {
            super(HttpStatus.NOT_FOUND, message);
        }
    }

    public static class WrongPasswordException extends UsersException {
        public WrongPasswordException(String message) {
            super(HttpStatus.UNAUTHORIZED, message);
        }
    }

    public static class InvalidCurrentPasswordException extends UsersException {
        public InvalidCurrentPasswordException(String message) {
            super(HttpStatus.CONFLICT, message);
        }
    }

    public static class SamePasswordException extends UsersException {
        public SamePasswordException(String message) {
            super(HttpStatus.CONFLICT, message);
        }
    }

    public static class SameNicknameException extends UsersException {
        public SameNicknameException(String message) {
            super(HttpStatus.CONFLICT, message);
        }
    }

    public static class SameProfileImgException extends UsersException {
        public SameProfileImgException(String message) {
            super(HttpStatus.CONFLICT, message);
        }
    }

    public static class UsersNicknameAlreadyExistsException extends UsersException {
        public UsersNicknameAlreadyExistsException(String message) {
            super(HttpStatus.CONFLICT, message);
        }
    }

}

