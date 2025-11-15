package com.soy.springcommunity.entity;

import com.soy.springcommunity.utils.PasswordUtil;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.List;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password_hash", length = 60, nullable = false)
    private String passwordHash;

    @Column(name = "nickname", length = 10, nullable = false, unique = true)
    private String nickname;

    @Column(name = "is_deleted" )
    private Boolean isDeleted;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Posts> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comments> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostLikes> postLikes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentLikes> commentLikes;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private UserDetail userDetail;
    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private FilesUserProfileImgUrl filesUserProfileImgUrl;


    @Builder
    public Users(String email, String password, String nickname){
        this.email = email;
        this.passwordHash = PasswordUtil.getHashedPassword(password);
        this.nickname = nickname;
        this.isDeleted = false;
    }

    public void setUserDetail(UserDetail userDetail) {
        this.userDetail = userDetail;
        if (userDetail.getUser() != this) {
            userDetail.setUser(this);
        }
    }

    public void updatePassword(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
        this.userDetail.setUpdatedAt();
    }

    public void softDelete() {
        this.isDeleted = true;
        this.userDetail.setDeletedAt();
    }

    public void updateProfile(String newNickname){
        this.nickname = newNickname;
        this.userDetail.setUpdatedAt();
    }

    public void updateProfileImgUrl(String newImgUrl){
        this.filesUserProfileImgUrl.setImgUrl(newImgUrl);
        this.userDetail.setUpdatedAt();
    }
}
