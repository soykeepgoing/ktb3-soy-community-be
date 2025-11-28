package com.soy.springcommunity.dto;

import com.soy.springcommunity.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class AdminUserInfoResponse {

    private Long id;
    private String nickname;
    private String email;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private LocalDateTime deletedTime;

    public static AdminUserInfoResponse from(Users user){
        return new AdminUserInfoResponse(
                user.getId(),
                user.getNickname(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getDeletedAt()
        );
    }
}
