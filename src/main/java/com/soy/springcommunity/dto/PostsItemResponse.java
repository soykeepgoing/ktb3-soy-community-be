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
    private String topicCode;
    private String content;
    private String userNickname;

    public static PostsItemResponse from(Posts posts) {
        return new PostsItemResponse(
                posts.getId(),
                posts.getTopic().getCode(),
                posts.getContent(),
                posts.getUser().getNickname()
        );
    }
}

