package com.soy.springcommunity.controller;

import com.soy.springcommunity.service.FilesService;
import com.soy.springcommunity.service.PostsService;
import com.soy.springcommunity.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
public class FilesController {
    private UsersService usersService;
    private PostsService postsService;
    private FilesService filesService;

    @Autowired
    public FilesController(
            UsersService usersService,
            PostsService postsService,
            FilesService filesService){
        this.usersService = usersService;
        this.postsService = postsService;
        this.filesService = filesService;
    }

    @PostMapping("/api/users/{userId}/profile")
    public ResponseEntity<Map<String, String>> uploadProfile(
            @PathVariable Long userId,
            @RequestPart("file") MultipartFile file) throws IOException {
        String url = filesService.saveFile(file);
        usersService.updateProfileImage(userId, url);
        return ResponseEntity.ok(Map.of("profileImgUrl", url));
    }

    @PostMapping("/api/posts/{postId}")
    public ResponseEntity<Map<String, String>> uploadPostImg(
            @PathVariable Long postId, @RequestPart("file") MultipartFile file) throws IOException {
        String url = filesService.saveFile(file);
        postsService.updatePostImage(postId, url);
        return ResponseEntity.ok(Map.of("profileImgUrl", url));
    }
}
