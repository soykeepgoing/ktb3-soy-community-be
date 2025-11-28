package com.soy.springcommunity.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
public class UsersSignInResponse {
    @Schema(description = "사용자 아이디", example = "1")
    private Long userId;

    @Schema(description = "사용자 닉네임", example = "소이")
    private String userNickname;

    @Schema(description = "사용자 프로필 이미지 url", example="example.com/example.png")
    private String userProfileImgUrl;


    public UsersSignInResponse() {}
    public UsersSignInResponse(Long userId, String userNickname, String userProfileImgUrl) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userProfileImgUrl = userProfileImgUrl;
    }

}

