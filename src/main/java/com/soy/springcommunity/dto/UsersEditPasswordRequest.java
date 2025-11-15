package com.soy.springcommunity.dto;

import com.soy.springcommunity.utils.ConstantUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class UsersEditPasswordRequest {
    @NotBlank
    private String userOldPassword;

    @NotBlank
    @Pattern(regexp = ConstantUtil.REGEX_PASSWORD, message = "비밀번호는 8자 이상 20자 이하, 대문자, 소문자, 특수문자 포함.")
    private String userNewPassword;
    public UsersEditPasswordRequest() {}
    public UsersEditPasswordRequest(String userOldPassword, String userNewPassword) {
        this.userOldPassword = userOldPassword;
        this.userNewPassword = userNewPassword;
    }
}
