package com.soy.springcommunity.service;

import com.soy.springcommunity.dto.*;
import com.soy.springcommunity.entity.FilesUserProfileImgUrl;
import com.soy.springcommunity.entity.Users;
import com.soy.springcommunity.exception.UsersException;
import com.soy.springcommunity.repository.files.FilesUserProfileImgRepository;
import com.soy.springcommunity.repository.users.UsersRepository;
import com.soy.springcommunity.utils.PasswordUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import static com.soy.springcommunity.utils.ConstantUtil.URL_DEFAULT_USER_PROFILE;

@Service
public class UsersService {
    private UsersRepository usersRepository;
    private FilesUserProfileImgRepository filesUserProfileImgRepository;

    @Autowired
    public UsersService(UsersRepository usersRepository,
                        FilesUserProfileImgRepository filesUserProfileImgRepository
                        ) {
        this.usersRepository = usersRepository;
        this.filesUserProfileImgRepository = filesUserProfileImgRepository;
    }

    private boolean isEmailExist(String email) {
        return usersRepository.findByEmailAndIsDeletedFalse(email).isPresent();
    }

    private boolean isNicknameExist(String nickname) {
        return usersRepository.findByNickname(nickname).isPresent();
    }

    @Transactional
    public UsersSignUpResponse signup(UsersSignUpRequest usersSignUpRequest) {
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

        // 데이터 저장
        Users user = Users.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();

        FilesUserProfileImgUrl filesUserProfileImgUrl =
                FilesUserProfileImgUrl.of(user, URL_DEFAULT_USER_PROFILE);

        usersRepository.save(user);
        filesUserProfileImgRepository.save(filesUserProfileImgUrl);

        return UsersSignUpResponse.create(user.getId(), nickname, user.getCreatedAt());
    }

    @Transactional
    public void updateProfileImage(Long userId, String url){
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new UsersException.UsersNotFoundException("존재하지 않는 사용자입니다."));
        users.updateProfileImgUrl(url);
    }

    private String[] getIssueAndExpirationTimes(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return new String[]{dtf.format(now), dtf.format(now.plusDays(7))};
    }

    private Map<String,String> getAuthInfoMap(){
        Map<String,String> authInfoMap = new HashMap<>();
        String[] timestamp = getIssueAndExpirationTimes();
        authInfoMap.put("issuedAt",timestamp[0]);
        authInfoMap.put("expiresIn",timestamp[1]);
        return authInfoMap;
    }

    public void verifyPassword(Users users, String givenPassword){
        if(!BCrypt.checkpw(givenPassword, users.getPasswordHash())){
            throw new UsersException.WrongPasswordException("잘못된 비밀번호입니다.");
        }
    }

    @Transactional
    public UsersSignInResponse signIn(UsersSignInRequest UsersSignInRequest) {

        String email = UsersSignInRequest.getUserEmail();
        String password = UsersSignInRequest.getUserPassword();

        Users users = usersRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new UsersException.UsersNotFoundException("존재하지 않는 사용자입니다."));
        verifyPassword(users, password);

        Map<String,String> authInfoMap = getAuthInfoMap();

        return new UsersSignInResponse(
                users.getId(),
                users.getNickname(),
                users.getFilesUserProfileImgUrl().getImgUrl(),
                authInfoMap.get("issuedAt"),
                authInfoMap.get("expiresIn")
        );
    }

    @Transactional
    public UsersSimpleResponse editPassword(Long id, UsersEditPasswordRequest usersEditPasswordRequest) {
        Users users = usersRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new UsersException.UsersNotFoundException("존재하지 않는 사용자입니다."));

        String oldPassword = usersEditPasswordRequest.getUserOldPassword();
        String newPassword = usersEditPasswordRequest.getUserNewPassword();

        verifyPassword(users, oldPassword);

        if (newPassword.equals(oldPassword)) {
            throw new UsersException.SamePasswordException("현재 비밀번호와 새 비밀번호가 동일합니다.");
        }

        String newPasswordHash = PasswordUtil.getHashedPassword(newPassword);
        users.updatePassword(newPasswordHash);

        return new UsersSimpleResponse(
                users.getId(),
                users.getNickname()
        );
    }

    @Transactional
    public UsersSimpleResponse editProfile(Long id, UsersEditProfileRequest usersEditProfileRequest) {
        Users users = usersRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new UsersException.UsersNotFoundException("존재하지 않는 사용자입니다."));

        String newNickname = usersEditProfileRequest.getUserNickname();

        if (newNickname.equals(users.getNickname())) {
            throw new UsersException.SameNicknameException("이전 닉네임과 동일합니다.");
        };

        if (usersRepository.findByNickname(newNickname).isPresent()) {
            throw new UsersException.UsersNicknameAlreadyExistsException("존재하는 닉네임입니다.");
        }

        users.updateProfile(newNickname);

        return new UsersSimpleResponse(
                users.getId(),
                users.getNickname()
        );
    }

    @Transactional
    public UsersSimpleResponse softDelete(Long id) {
        Users users = usersRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new UsersException.UsersNotFoundException("존재하지 않는 사용자입니다."));
        users.softDelete();

        return new UsersSimpleResponse(
                users.getId(),
                users.getNickname()
        );
    }

}
