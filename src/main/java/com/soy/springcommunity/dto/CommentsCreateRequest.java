package com.soy.springcommunity.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CommentsCreateRequest {
    @NotBlank
    private String commentContent;

    public CommentsCreateRequest() {}
    public CommentsCreateRequest(String commentContent) {
        this.commentContent = commentContent;
    }
}
