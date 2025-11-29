package com.soy.springcommunity.controller;

import com.soy.springcommunity.dto.*;
import com.soy.springcommunity.entity.CustomUserDetails;
import com.soy.springcommunity.service.FilesService;
import com.soy.springcommunity.service.PostsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

import static com.soy.springcommunity.utils.ConstantUtil.URL_DEFAULT_POST_IMG;
import static com.soy.springcommunity.utils.ConstantUtil.URL_DEFAULT_USER_PROFILE;

@RestController
@RequestMapping("/api/posts")
public class PostsController {
    private PostsService postsService;
    private FilesService filesService;

    @Autowired
    public PostsController(PostsService postsService,
                           FilesService filesService) {
        this.postsService = postsService;
        this.filesService = filesService;
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
    public ResponseEntity<PostsCreateResponse> createPost(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @RequestPart("data") @Valid PostsCreateRequest PostsCreateRequest,
            @RequestPart(value = "postImgFile", required = false) MultipartFile postImgFile
            ) {
        Long userId = userDetails.getUser().getId();
        String postImgUrl = URL_DEFAULT_POST_IMG;
        try{
            postImgUrl = filesService.saveFile(postImgFile);
        } catch (Exception e) {
        }

        PostsCreateResponse postCreateResponse = postsService.createPost(userId, PostsCreateRequest, postImgUrl);

        return ResponseEntity
                .created(URI.create(postCreateResponse.getRedirectUri()))
                .body(postCreateResponse);
    }

    @PatchMapping("/{postId}")
    @Operation(summary = "게시글 수정")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공")
    })
    public ResponseEntity<SimpleResponse> editPost(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @PathVariable("postId") Long postId,
            @Valid @RequestBody PostsEditRequest postEditRequest) {
        Long userId = userDetails.getUser().getId();
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
