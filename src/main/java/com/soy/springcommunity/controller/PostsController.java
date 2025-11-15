package com.soy.springcommunity.controller;

import com.soy.springcommunity.dto.*;
import com.soy.springcommunity.service.PostsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/posts")
public class PostsController {
    private PostsService postsService;
    @Autowired
    public PostsController(PostsService postsService) {
        this.postsService = postsService;
    }

    @GetMapping("")
    @Operation(summary = "게시글 목록 보기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 목록 보기 성공")
    })
    public ResponseEntity<PostsListResponse> getPostList(@RequestParam int page, @RequestParam int size) {
        PostsListResponse PostsListResponse = postsService.viewPostList(page, size);
        return ResponseEntity.ok(PostsListResponse);
    }

    @GetMapping("/{postId}")
    @Operation(summary = "게시글 상세 보기")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 상세 보기 성공")
    })
    public ResponseEntity<PostsDetailResponse> getPostDetail(@PathVariable("postId") Long postId, @RequestParam Long userId) {
        PostsDetailResponse PostsDetailResponse = postsService.viewPostDetail(postId, userId);
        return ResponseEntity.ok(PostsDetailResponse);
    }

    @PostMapping("")
    @Operation(summary = "게시글 생성")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 생성 성공")
    })
    public ResponseEntity<PostsCreateResponse> createPost(@RequestParam Long userId, @RequestBody PostsCreateRequest PostsCreateRequest) {
        PostsCreateResponse postCreateResponse = postsService.createPost(userId, PostsCreateRequest);
        return ResponseEntity
                .created(URI.create(postCreateResponse.getRedirectUri()))
                .body(postCreateResponse);
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "게시글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "게시글 수정 성공")
    })
    public ResponseEntity<SimpleResponse> editPost(@PathVariable("postId") Long postId, @RequestParam Long userId, @RequestBody PostsEditRequest postEditRequest) {
        SimpleResponse simpleResponse = postsService.editPost(postId, userId, postEditRequest);
        return ResponseEntity.ok(simpleResponse);
    }

    @DeleteMapping("/{postId}")
    @Operation(summary = "게시글 삭제")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공")
    })
    public ResponseEntity<SimpleResponse> deletePost(@PathVariable("postId") Long postId, @RequestParam Long userId) {
        SimpleResponse simpleResponse = postsService.deletePost(postId, userId);
        return ResponseEntity.ok(simpleResponse);
    }
}
