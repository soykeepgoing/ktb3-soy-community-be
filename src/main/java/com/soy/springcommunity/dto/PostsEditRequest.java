package com.soy.springcommunity.dto;

import lombok.Getter;

@Getter
public class PostsEditRequest {
    private String postContent;
    private String postImageUrl;

    public PostsEditRequest() {}
    public PostsEditRequest(String postContent, String postImageUrl) {
        this.postContent = postContent;
        this.postImageUrl = postImageUrl;
    }
}
