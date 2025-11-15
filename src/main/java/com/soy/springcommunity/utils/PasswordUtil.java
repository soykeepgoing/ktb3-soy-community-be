package com.soy.springcommunity.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordUtil {
    public static String getHashedPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}
