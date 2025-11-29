package com.soy.springcommunity.controller;

import com.soy.springcommunity.dto.*;
import com.soy.springcommunity.entity.CustomUserDetails;
import com.soy.springcommunity.entity.Users;
import com.soy.springcommunity.service.FilesService;
import io.swagger.v3.oas.annotations.Operation;
import com.soy.springcommunity.service.UsersService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collections;
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

    @Operation(summary = "비밀번호 변경")
    @PatchMapping("/me/password")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "비밀번호 변경 성공")
    })
    public ResponseEntity<ApiCommonResponse<UsersSimpleResponse>> editPassword(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UsersEditPasswordRequest usersEditPasswordRequest) {
        UsersSimpleResponse UsersSimpleResponse = usersService.editPassword(userDetails, usersEditPasswordRequest);

        return UsersApiResponse.ok(HttpStatus.OK,
                "비밀번호 변경 성공",
                UsersSimpleResponse);
    }

    @Operation(summary = "프로필 변경")
    @PatchMapping("/me/profile")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "프로필 변경 성공")
    })
    public ResponseEntity<ApiCommonResponse<UsersSimpleResponse>> editProfile(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            @Valid @RequestBody UsersEditProfileRequest usersEditProfileRequest) {
        UsersSimpleResponse UsersSimpleResponse = usersService.editProfile(userDetails, usersEditProfileRequest);
        return UsersApiResponse.ok(HttpStatus.OK,
                "프로필 변경 성공",
                UsersSimpleResponse);
    }

    @Operation(summary = "회원 삭제")
    @DeleteMapping("")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "삭제 성공")
    })
    public ResponseEntity<ApiCommonResponse<UsersSimpleResponse>> softDelete(
            @AuthenticationPrincipal CustomUserDetails userDetails,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        UsersSimpleResponse UsersSimpleResponse = usersService.softDelete(userDetails);

        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }

        SecurityContextHolder.clearContext();

        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        return UsersApiResponse.ok(HttpStatus.OK,
                "회원 삭제 성공",
                UsersSimpleResponse);
    }
}
