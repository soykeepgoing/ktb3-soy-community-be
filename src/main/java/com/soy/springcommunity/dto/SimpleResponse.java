package com.soy.springcommunity.dto;

public class SimpleResponse {
    private String useCase;
    private Long postId;
    private Long userId;

    public SimpleResponse() {}

    public SimpleResponse(String useCase, Long postId, Long userId) {
        this.useCase = useCase;
        this.postId = postId;
        this.userId = userId;
    }

    public static SimpleResponse forEditPost(Long userId, Long postId) {
        return new SimpleResponse("EditPost", postId, userId);
    }

    public static SimpleResponse forDeletePost(Long userId, Long postId) {
        return new SimpleResponse("DeletePost", postId, userId);
    }

    public static SimpleResponse forEditComment(Long userId, Long comemntId) {
        return new SimpleResponse("EditComments", comemntId, userId);
    }

    public static SimpleResponse forDeleteComment(Long userId, Long comemntId) {
        return new SimpleResponse("DeleteComments", comemntId, userId);
    }

    public String getUseCase() {return useCase;}
    public void setUseCase(String useCase) {this.useCase = useCase;}
    public Long getPostId() {return postId;}
    public void setPostId(Long postId) {this.postId = postId;}
    public Long getUserId() {return userId;}
    public void setUserId(Long userId) {this.userId = userId;}
}
