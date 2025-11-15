package com.soy.springcommunity.controller;

import com.soy.springcommunity.dto.*;
import com.soy.springcommunity.service.FilesService;
import io.swagger.v3.oas.annotations.Operation;
import com.soy.springcommunity.service.UsersService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private UsersService usersService;

    @Autowired
    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }

    @Operation(summary = "회원가입")
    @PostMapping("")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "가입 성공")
    })
    public ResponseEntity<ApiCommonResponse<UsersSignUpResponse>> signUp(@Valid @RequestBody UsersSignUpRequest usersSignUpRequest) throws IOException {
        UsersSignUpResponse signUpResponse = usersService.signup(usersSignUpRequest);
        return UsersApiResponse.created(
                HttpStatus.OK,
                "회원가입 성공",
                signUpResponse);
    }


    @Operation(summary = "로그인")
    @PostMapping("/auth")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "로그인 성공")
    })
    public ResponseEntity<ApiCommonResponse<UsersSignInResponse>> signIn(@Valid @RequestBody UsersSignInRequest UsersSignInRequest) {
        UsersSignInResponse signInResponse = usersService.signIn(UsersSignInRequest);
        return UsersApiResponse.created(HttpStatus.CREATED,
                "로그인 성공",
                signInResponse);
    }

    @Operation(summary = "비밀번호 변경")
    @PatchMapping("/{id}/password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공")
    })
    public ResponseEntity<ApiCommonResponse<UsersSimpleResponse>> editPassword(@PathVariable Long id, @Valid @RequestBody UsersEditPasswordRequest UsersEditPasswordRequest) {
        UsersSimpleResponse UsersSimpleResponse = usersService.editPassword(id, UsersEditPasswordRequest);
        return UsersApiResponse.ok(HttpStatus.OK,
                "비밀번호 변경 성공",
                UsersSimpleResponse);
    }

    @Operation(summary = "프로필 변경")
    @PatchMapping("/{id}/profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 변경 성공")
    })
    public ResponseEntity<ApiCommonResponse<UsersSimpleResponse>> editProfile(@Valid @RequestBody UsersEditProfileRequest usersEditProfileRequest, @PathVariable Long id) {
        UsersSimpleResponse UsersSimpleResponse = usersService.editProfile(id, usersEditProfileRequest);
        return UsersApiResponse.ok(HttpStatus.OK,
                "프로필 변경 성공",
                UsersSimpleResponse);
    }

    @Operation(summary = "회원 삭제")
    @DeleteMapping("/{id}")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공")
    })
    public ResponseEntity<ApiCommonResponse<UsersSimpleResponse>> softDelete(@PathVariable Long id) {
        System.out.println(id);
        UsersSimpleResponse UsersSimpleResponse = usersService.softDelete(id);
        return UsersApiResponse.ok(HttpStatus.OK,
                "회원 삭제 성공",
                UsersSimpleResponse);
    }
}
