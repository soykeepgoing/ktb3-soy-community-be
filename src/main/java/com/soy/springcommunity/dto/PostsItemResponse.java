package com.soy.springcommunity.dto;

import com.soy.springcommunity.entity.PostStats;
import com.soy.springcommunity.entity.Posts;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Getter
@Schema(description = "게시글 아이템 DTO")
@AllArgsConstructor
public class PostsItemResponse {

    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private String userNickname;
    private String userProfileImgUrl;
    private Long statsViewCounts;
    private Long statsLikeCounts;
    private Long statsCommentCounts;

    public static PostsItemResponse from(Posts posts) {
        return new PostsItemResponse(
                posts.getId(),
                posts.getTitle(),
                posts.getCreatedAt(),
                posts.getUser().getNickname(),
                posts.getUser().getFilesUserProfileImgUrl().getImgUrl(),
                posts.getPostStats().getViewCount(),
                posts.getPostStats().getLikeCount(),
                posts.getPostStats().getCommentCount()
        );
    }
}

