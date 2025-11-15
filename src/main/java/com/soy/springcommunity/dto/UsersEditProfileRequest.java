package com.soy.springcommunity.dto;

import com.soy.springcommunity.utils.ConstantUtil;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Getter
public class UsersEditProfileRequest {
    @NotBlank
    @Length(max= ConstantUtil.NICKNAME_MAX_LEN)
    private String userNickname;

    public UsersEditProfileRequest() {}
    public UsersEditProfileRequest(String userNickname){
        this.userNickname = userNickname;
    }
}
