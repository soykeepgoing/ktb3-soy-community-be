package com.soy.springcommunity.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AdminUserListResponse {
    private List<AdminUserInfoResponse> userInfoList = new ArrayList<>();
    public AdminUserListResponse() {}
    public AdminUserListResponse(List<AdminUserInfoResponse> userInfoList) {
        this.userInfoList = userInfoList;
    }

    public void add(AdminUserInfoResponse adminUserInfoResponse) {
        this.userInfoList.add(adminUserInfoResponse);
    }
}
