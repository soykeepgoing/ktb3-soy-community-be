package com.soy.springcommunity.dto;

import com.soy.springcommunity.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class UsersApiResponse {
    public static <T> ResponseEntity<ApiCommonResponse<T>> ok(HttpStatus status, String message, T data) {
        return ResponseEntity.status(status)
                .body(ApiCommonResponse.success(status.value(), message, data));
    }

    public static <T> ResponseEntity<ApiCommonResponse<T>> created(HttpStatus status, String message, T data){
        return ResponseEntity.status(status)
                .body(ApiCommonResponse.success(status.value(), message, data));
    }

    public static <T> ResponseEntity<ApiCommonResponse<T>> fail(BusinessException e) {
        return ResponseEntity.status(e.getStatus())
                .body(ApiCommonResponse.fail(e.getStatus().value(), e.getMessage()));
    }
}
