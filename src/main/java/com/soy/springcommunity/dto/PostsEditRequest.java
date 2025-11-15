package com.soy.springcommunity.dto;

import lombok.Getter;

@Getter
public class PostsEditRequest {
    private String postTitle;
    private String postContent;
    private String postImageUrl;

    public PostsEditRequest() {}
    public PostsEditRequest(String postTitle, String postContent, String postImageUrl) {
        this.postTitle = postTitle;
        this.postContent = postContent;
        this.postImageUrl = postImageUrl;
    }
}
