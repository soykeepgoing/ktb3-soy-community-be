package com.soy.springcommunity.dto;

import com.soy.springcommunity.entity.PostStats;
import com.soy.springcommunity.entity.Posts;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Schema(description = "게시글 상세 조회 응답 DTO")
public class PostsDetailResponse {

    @Schema(description = "게시글 식별자", example = "101")
    private Long id;
    @Schema(description = "토픽 코드", example = "topic code")
    private String topicCode;
    @Schema(description = "토픽 라벨", example = "topic label")
    private String topicLabel;
    @Schema(description = "게시글 내용", example = "content")
    private String content;

    @Schema(description = "게시글 이미지")
    private String imgUrl;

    @Schema(description = "게시글 생성일시", example = "202510101010")
    private LocalDateTime createdAt;

    private String userNickname;
    private String userProfileImgUrl;

    private Long statsViewCounts;
    private Long statsLikeCounts;
    private Long statsCommentCounts;

    private Boolean isUserLiked;

    public static PostsDetailResponse of(Posts post, Boolean isUserLiked) {
        return new PostsDetailResponse(
                post.getId(),
                post.getTopic().getCode(),
                post.getTopic().getLabel(),
                post.getContent(),
                post.getFilesPostImgUrl().getImgUrl(),
                post.getCreatedAt(),
                post.getUser().getNickname(),
                post.getUser().getFilesUserProfileImgUrl().getImgUrl(),
                post.getPostStats().getViewCount(),
                post.getPostStats().getLikeCount(),
                post.getPostStats().getCommentCount(),
                isUserLiked
        );
    }
}

