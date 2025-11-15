package com.soy.springcommunity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class UsersSignInRequest {

    @NotBlank
    @Email
    private String userEmail;

    @NotBlank
    private String userPassword;

    public UsersSignInRequest() {}
    public UsersSignInRequest(String email, String password) {
        this.userEmail = email;
        this.userPassword = password;
    }
}

