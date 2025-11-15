package com.soy.springcommunity.controller;

import com.soy.springcommunity.dto.*;
import com.soy.springcommunity.service.CommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<CommentsCreateResponse> createComments(@RequestBody CommentsCreateRequest createCommentRequest,
                                                                 @PathVariable("postId") Long postId,
                                                                 @RequestParam Long userId,
                                                                 @RequestParam(value = "commentId" , required = false) Long parentCommentId) {
        CommentsCreateResponse createCommentResponse = commentsService.createComments(createCommentRequest, postId, userId, parentCommentId);
        return ResponseEntity.created(URI.create("/posts/" + postId))
                .body(createCommentResponse);
    }

    @PatchMapping("/api/posts/{postId}/comments/{commentId}")
    public ResponseEntity<SimpleResponse> editComments(@RequestBody CommentsEditRequest editCommentRequest,
                                                       @PathVariable("postId") Long postId,
                                                       @PathVariable("commentId") Long commentId,
                                                       @RequestParam Long userId){
        SimpleResponse simpleResponse = commentsService.editComments(editCommentRequest, postId, commentId, userId);
        return ResponseEntity.ok(simpleResponse);
    }

    @DeleteMapping("/api/posts/{postId}/comments/{commentId}")
    public ResponseEntity<SimpleResponse> deleteComments(@PathVariable("postId") Long postId,
                                                         @PathVariable Long commentId,
                                                         @RequestParam Long userId){
        SimpleResponse simpleResponse = commentsService.deleteComments(postId, commentId, userId);
        return ResponseEntity.ok(simpleResponse);
    }

}

