package com.soy.springcommunity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "게시글 생성 응답 DTO")
public class PostsCreateResponse {

    @Schema(
            description = "응답 메시지",
            example = "게시글이 성공적으로 생성되었습니다."
    )
    private String message;

    @Schema(
            description = "생성된 게시글의 식별자",
            example = "123"
    )
    private Long postId;

    @Schema(
            description = "생성 후 리다이렉트할 URI",
            example = "/api/posts/123"
    )
    private String redirectUri;

    private PostsCreateResponse(String message, Long postId, String redirectUri) {
        this.message = message;
        this.postId = postId;
        this.redirectUri = redirectUri;
    }

    public static PostsCreateResponse of(Long postId) {
        return new PostsCreateResponse(
                "게시글이 성공적으로 생성되었습니다.", // 반환 메시지
                postId,
                "/posts/" + postId
        );
    }
}

