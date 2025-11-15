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
    @Schema(description = "사용자 로그인 일시", example = "202510212147")
    private String userIssuedAt;
    @Schema(description = "사용자 로그인 만료 일시", example = "202510282147")
    private String userExpiresIn;

    public UsersSignInResponse() {}
    public UsersSignInResponse(Long userId, String userNickname, String userProfileImgUrl, String issuedAt, String expiresIn) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userProfileImgUrl = userProfileImgUrl;
        this.userIssuedAt = issuedAt;
        this.userExpiresIn = expiresIn;
    }
}

