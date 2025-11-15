package com.soy.springcommunity.dto;

import com.soy.springcommunity.entity.Comments;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "댓글 보기 응답 dto")
public class CommentsViewResponse {
    @Schema(description = "댓글 리스트")
    private List<CommentsItemResponse> commentsItemResponseList;

    public CommentsViewResponse(List<CommentsItemResponse> commentsItemResponseList) {
        this.commentsItemResponseList = commentsItemResponseList;
    }
}

