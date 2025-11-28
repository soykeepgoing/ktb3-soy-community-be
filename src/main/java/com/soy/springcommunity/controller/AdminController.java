package com.soy.springcommunity.controller;

import com.soy.springcommunity.dto.AdminUserListResponse;
import com.soy.springcommunity.dto.ApiCommonResponse;
import com.soy.springcommunity.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private AdminService adminService;
    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }
    
    @Operation(summary = "관리자 회원 조회")
    @GetMapping("/members")
    @ApiResponse
    public ResponseEntity<AdminUserListResponse> findAllMembers(){
        AdminUserListResponse adminUserListResponse = this.adminService.findAllUsers();
        return new ResponseEntity<>(adminUserListResponse, HttpStatus.OK);
    }

    

}
