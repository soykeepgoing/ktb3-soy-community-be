package com.soy.springcommunity.dto;

import lombok.Getter;

@Getter
public class PostsWriterSummary {
    private long userId;
    private String userNickname;
    private String userProfileImgUrl;

    private PostsWriterSummary(long userId, String userNickname, String userProfileImgUrl) {
        this.userId = userId;
        this.userNickname = userNickname;
        this.userProfileImgUrl = userProfileImgUrl;
    }

    public static PostsWriterSummary create(long userId, String userNickname, String userProfileImgUrl) {
        return new PostsWriterSummary(userId, userNickname, userProfileImgUrl);
    }
}

