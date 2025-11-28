package com.soy.springcommunity.service;

import com.soy.springcommunity.dto.AdminUserInfoResponse;
import com.soy.springcommunity.dto.AdminUserListResponse;
import com.soy.springcommunity.entity.Users;
import com.soy.springcommunity.repository.users.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private UsersRepository usersRepository;

    @Autowired
    public AdminService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Transactional
    public AdminUserListResponse findAllUsers(){
        List<Users> users = usersRepository.findAll();
        AdminUserListResponse adminUserListResponse = new AdminUserListResponse();

        for (Users user:users){
            AdminUserInfoResponse adminUserInfoResponse = AdminUserInfoResponse.from(user);
            adminUserListResponse.add(adminUserInfoResponse);
        }

        return adminUserListResponse;

    }


}
