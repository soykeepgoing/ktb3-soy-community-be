package com.soy.springcommunity.dto;

import com.soy.springcommunity.entity.Comments;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "댓글 아이템 DTO")
@AllArgsConstructor
public class CommentsItemResponse {
    private Long id;
    private String body;
    private String userNickname;
    private String userProfileImgUrl;
    private LocalDateTime createdAt;

    public static CommentsItemResponse from(Comments comments){
        return new CommentsItemResponse(
                comments.getId(),
                comments.getBody(),
                comments.getUser().getNickname(),
                comments.getUser().getFilesUserProfileImgUrl().getImgUrl(),
                comments.getCreatedAt()
        );
    }






}
