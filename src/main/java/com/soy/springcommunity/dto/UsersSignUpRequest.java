package com.soy.springcommunity.dto;

import com.soy.springcommunity.utils.ConstantUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

@Builder
@Getter
public class UsersSignUpRequest {
    @NotBlank
    @Pattern(regexp= ConstantUtil.REGEX_EMAIL, message = ConstantUtil.MSG_EMAIL_NOT_VALID)
    private String userEmail;

    @NotBlank()
    @Pattern(regexp = ConstantUtil.REGEX_PASSWORD, message = ConstantUtil.MSG_PW_NOT_VALID)
    private String userPassword;

    @NotBlank
    @Length(max=10)
    private String userNickname;
}


