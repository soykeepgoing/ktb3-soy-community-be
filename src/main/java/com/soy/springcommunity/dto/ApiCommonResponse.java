package com.soy.springcommunity.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApiCommonResponse<T> {
    private int code;
    private String message;
    private T data;

    public static <T> ApiCommonResponse<T> success(int code, String message, T data) {
        return new ApiCommonResponse<>(code, message, data);
    }

    public static <T> ApiCommonResponse<T> fail(int code, String message) {
        return new ApiCommonResponse<>(code, message, null);
    }
}
