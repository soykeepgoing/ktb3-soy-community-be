package com.soy.springcommunity.controller;

import com.soy.springcommunity.dto.*;
import com.soy.springcommunity.entity.CustomUserDetails;
import com.soy.springcommunity.service.CommentsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class CommentsController {
    private CommentsService commentsService;
    @Autowired
    public CommentsController(CommentsService commentsService) {
        this.commentsService = commentsService;
    }

    @GetMapping("/api/posts/{postId}/comments")
    public ResponseEntity<List<CommentsItemResponse>> viewComments(@PathVariable("postId") Long postId) {
        List<CommentsItemResponse> commentsItemResponseList = commentsService.viewComments(postId);
        return ResponseEntity.ok(commentsItemResponseList);
    }

    @PostMapping("/api/posts/{postId}/comments")
    public ResponseEntity<CommentsCreateResponse> createComments(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CommentsCreateRequest createCommentRequest,
            @PathVariable("postId") Long postId,
            @RequestParam(value = "commentId" , required = false) Long parentCommentId) {
        CommentsCreateResponse createCommentResponse = commentsService.createComments(userDetails, createCommentRequest, postId, parentCommentId);
        return ResponseEntity.created(URI.create("/posts/" + postId))
                .body(createCommentResponse);
    }

    @PatchMapping("/api/posts/{postId}/comments/{commentId}")
    public ResponseEntity<SimpleResponse> editComments(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody CommentsEditRequest editCommentRequest,
            @PathVariable("postId") Long postId,
            @PathVariable("commentId") Long commentId){
        SimpleResponse simpleResponse = commentsService.editComments(userDetails, editCommentRequest, postId, commentId);
        return ResponseEntity.ok(simpleResponse);
    }

    @DeleteMapping("/api/posts/{postId}/comments/{commentId}")
    public ResponseEntity<SimpleResponse> deleteComments(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("postId") Long postId,
            @PathVariable Long commentId){
        SimpleResponse simpleResponse = commentsService.deleteComments(userDetails, postId, commentId);
        return ResponseEntity.ok(simpleResponse);
    }

}

