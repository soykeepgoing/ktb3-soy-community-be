package com.soy.springcommunity.dto;

import lombok.Getter;

@Getter
public class PostsEditRequest {
    private String postContent;
    private String postImgUrl;

    public PostsEditRequest() {}
    public PostsEditRequest(String postContent, String postImgUrl) {
        this.postContent = postContent;
        this.postImgUrl = postImgUrl;
    }
}
