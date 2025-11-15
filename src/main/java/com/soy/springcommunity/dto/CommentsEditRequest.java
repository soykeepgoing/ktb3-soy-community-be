package com.soy.springcommunity.dto;

import lombok.Getter;

@Getter
public class CommentsEditRequest {
    private String newCommentContent;
    public CommentsEditRequest() {}
    public CommentsEditRequest(String newCommentContent) {
        this.newCommentContent = newCommentContent;
    }
}

