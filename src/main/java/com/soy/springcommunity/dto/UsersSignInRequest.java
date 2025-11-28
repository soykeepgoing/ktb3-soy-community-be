package com.soy.springcommunity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsersSignInRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String password;

    public UsersSignInRequest() {}
    public UsersSignInRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}

