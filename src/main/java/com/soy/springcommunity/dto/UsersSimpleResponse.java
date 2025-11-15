package com.soy.springcommunity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UsersSimpleResponse {

    @Schema(description = "사용자 아이디", example = "1")
    private Long userId;
    @Schema(description = "사용자 닉네임", example = "test1")
    private String userNickname;

    public UsersSimpleResponse() {}
    public UsersSimpleResponse(Long userId, String userNickname) {
        this.userId = userId;
        this.userNickname = userNickname;
    }
}
