package com.soy.springcommunity.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@Schema(description = "게시글 페이징 메타데이터")
public class PostsPagingMetaResponse {

    @Schema(description = "현재 페이지 번호 (0부터 시작)", example = "0")
    private int pageNumber;

    @Schema(description = "한 페이지에 포함되는 게시글 수", example = "20")
    @Builder.Default
    private int pageSize = 20;

    @Schema(description = "정렬 기준 (예: createdAt,desc)", example = "createdAt,desc")
    @Builder.Default
    private String sortCondition = "createdAt,desc";

}