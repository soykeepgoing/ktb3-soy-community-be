package com.soy.springcommunity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UsersSignUpResponse {
    @Schema(description = "사용자 아이디", example = "1")
    private Long userId;
    @Schema(description = "사용자 닉네임", example = "test1")
    private String userNickname;
    @Schema(description = "사용자 생성일시", example = "202510212147")
    private LocalDateTime userCreatedAt;

    private UsersSignUpResponse(Long userId, String userNickname, LocalDateTime userCreatedAt) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userCreatedAt = userCreatedAt;
    }

    public static UsersSignUpResponse create(Long userId, String nickname, LocalDateTime createdAt) {
        return new UsersSignUpResponse(userId, nickname, createdAt);
    }
}

