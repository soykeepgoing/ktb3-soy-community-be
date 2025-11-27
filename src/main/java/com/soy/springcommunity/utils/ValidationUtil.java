package com.soy.springcommunity.utils;

public class ValidationUtil {
    public static boolean isBlank(String content) {
        return content != null && !content.trim().isEmpty();
    }


}
