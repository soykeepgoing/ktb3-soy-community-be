package com.soy.springcommunity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "게시글 목록보기 DTO")
public class PostsListResponse {

    @Schema(description = "게시글 리스트")
    private List<PostsItemResponse> postItemResponseList;
    @Schema(description = "게시글 페이징 메타데이터")
    private PostsPagingMetaResponse pagingMeta;

    public PostsListResponse() {}
    public PostsListResponse(List<PostsItemResponse> postItemResponseList, PostsPagingMetaResponse pagingMeta) {
        this.postItemResponseList = postItemResponseList;
        this.pagingMeta = pagingMeta;
    }
}
