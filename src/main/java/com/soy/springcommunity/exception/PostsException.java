package com.soy.springcommunity.exception;

import org.springframework.http.HttpStatus;

public class PostsException extends BusinessException {

    public PostsException(HttpStatus status, String message) {
        super(status, message);
    }

    public static class PostsGoneException extends PostsException {
        public PostsGoneException(String message) {
            super(HttpStatus.GONE, message);
        }
    }

    public static class PostsNotFoundException extends PostsException {
        public PostsNotFoundException(String message) {
            super(HttpStatus.NOT_FOUND, message);
        }
    }

    public static class PostsNotAuthorizedException extends PostsException {
        public PostsNotAuthorizedException(String message) {
            super(HttpStatus.FORBIDDEN, message);
        }
    }

    public static class NoEditPostsException extends PostsException {
        public NoEditPostsException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
    }


}

