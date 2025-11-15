package com.soy.springcommunity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "댓글 생성 응답 DTO")
public class CommentsCreateResponse {

    @Schema(description = "댓글 식별자", example = "1")
    private Long id;

    @Schema(description = "댓글 내용", example = ".")
    private String body;

    @Schema(description = "댓글 작성자 프로필 사진", example = "image/example.com")
    private String userProfileImgUrl;

    @Schema(description = "댓글 작성자 닉네임", example = "소이")
    private String userNickname;

    @Schema(description = "댓글 작성일시", example = "20251104")
    private LocalDateTime createdAt;

    private CommentsCreateResponse(Long id, String body, String userProfileImgUrl, String userNickname, LocalDateTime createdAt) {
        this.id = id;
        this.body = body;
        this.userProfileImgUrl = userProfileImgUrl;
        this.userNickname = userNickname;
        this.createdAt = createdAt;
    }

    public static CommentsCreateResponse of(Long id, String body, String userProfileImgUrl, String userNickname, LocalDateTime createdAt) {
        return new CommentsCreateResponse(
                id,
                body,
                userProfileImgUrl,
                userNickname,
                createdAt
        );
    }
}
