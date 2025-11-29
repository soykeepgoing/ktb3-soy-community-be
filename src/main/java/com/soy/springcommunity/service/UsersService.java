package com.soy.springcommunity.service;

import com.soy.springcommunity.dto.*;
import com.soy.springcommunity.entity.CustomUserDetails;
import com.soy.springcommunity.entity.FilesUserProfileImgUrl;
import com.soy.springcommunity.entity.Users;
import com.soy.springcommunity.exception.UsersException;
import com.soy.springcommunity.repository.files.FilesUserProfileImgRepository;
import com.soy.springcommunity.repository.users.UsersRepository;
import com.soy.springcommunity.utils.PasswordUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.soy.springcommunity.utils.ConstantUtil.URL_DEFAULT_USER_PROFILE;
import static com.soy.springcommunity.utils.PasswordUtil.getHashedPassword;

@Service
public class UsersService {
    private UsersRepository usersRepository;
    private FilesUserProfileImgRepository filesUserProfileImgRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UsersService(UsersRepository usersRepository,
                        FilesUserProfileImgRepository filesUserProfileImgRepository,
                        PasswordEncoder passwordEncoder
    ) {
        this.usersRepository = usersRepository;
        this.filesUserProfileImgRepository = filesUserProfileImgRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private boolean isEmailExist(String email) {
        return usersRepository.findByEmailAndIsDeletedFalse(email).isPresent();
    }

    private boolean isNicknameExist(String nickname) {
        return usersRepository.findByNickname(nickname).isPresent();
    }

    @Transactional
    public UsersSignUpResponse signup(UsersSignUpRequest usersSignUpRequest, String userProfileImgUrl) {
        String email = usersSignUpRequest.getUserEmail();
        String password = usersSignUpRequest.getUserPassword();
        String nickname = usersSignUpRequest.getUserNickname();

        nickname = nickname.trim();
        password = password.trim();
        email = email.trim();

        if (isEmailExist(email)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다.");
        }

        if(isNicknameExist(nickname)){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다.");
        }

        password = getHashedPassword(password);

        // 데이터 저장
        Users user = Users.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();

        FilesUserProfileImgUrl filesUserProfileImgUrl =
                FilesUserProfileImgUrl.of(user, userProfileImgUrl);

        usersRepository.save(user);
        filesUserProfileImgRepository.save(filesUserProfileImgUrl);

        return UsersSignUpResponse.create(user.getId(), nickname, user.getCreatedAt());
    }

    @Transactional
    public void updateProfileImage(CustomUserDetails userDetails, String url){
        Long id = userDetails.getUser().getId();
        Users user = usersRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.updateProfileImgUrl(url);
    }

    @Transactional
    public UsersSimpleResponse editPassword(
            CustomUserDetails userDetails,
            UsersEditPasswordRequest usersEditPasswordRequest) {

        Long id = userDetails.getUser().getId();
        Users user = usersRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String oldPassword = usersEditPasswordRequest.getUserOldPassword();
        String newPassword = usersEditPasswordRequest.getUserNewPassword();

        if (newPassword.equals(oldPassword)) {
            throw new UsersException.SamePasswordException("현재 비밀번호와 새 비밀번호가 동일합니다.");
        }

        String newPasswordHash = getHashedPassword(newPassword);
        user.updatePassword(newPasswordHash);

        return new UsersSimpleResponse(
                user.getId(),
                user.getNickname()
        );
    }

    @Transactional
    public UsersSimpleResponse editProfile(
            CustomUserDetails userDetails,
            UsersEditProfileRequest usersEditProfileRequest) {

        Long id = userDetails.getUser().getId();
        Users user = usersRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        String newNickname = usersEditProfileRequest.getUserNickname();

        if (newNickname.equals(user.getNickname())) {
            throw new UsersException.SameNicknameException("이전 닉네임과 동일합니다.");
        };

        if (usersRepository.findByNickname(newNickname).isPresent()) {
            throw new UsersException.UsersNicknameAlreadyExistsException("존재하는 닉네임입니다.");
        }

        user.updateProfile(newNickname);

        return new UsersSimpleResponse(
                user.getId(),
                user.getNickname()
        );
    }

    @Transactional
    public UsersSimpleResponse softDelete(CustomUserDetails userDetails) {
        Long id = userDetails.getUser().getId();
        Users user = usersRepository.findById(id).
                orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        user.softDelete();
        return new UsersSimpleResponse(
                user.getId(),
                user.getNickname()
        );
    }

}
