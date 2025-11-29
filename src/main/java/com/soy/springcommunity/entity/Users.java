package com.soy.springcommunity.entity;

import com.soy.springcommunity.utils.PasswordUtil;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.time.LocalDateTime;
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

    @Column(name="role")
    @Enumerated(EnumType.STRING)
    private Role role = Role.USER;

    @Column(name = "nickname", length = 10, nullable = false, unique = true)
    private String nickname;

    @Column(name = "is_deleted" )
    private Boolean isDeleted;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Posts> posts;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comments> comments;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<PostLikes> postLikes;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<CommentLikes> commentLikes;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private FilesUserProfileImgUrl filesUserProfileImgUrl;


    @Builder
    public Users(String email, String password, String nickname){
        this.email = email;
        this.passwordHash = password;
        this.nickname = nickname;
        this.isDeleted = false;
        this.createdAt = LocalDateTime.now();
    }

    protected void markUpdated(){
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePassword(String newPasswordHash) {
        this.passwordHash = newPasswordHash;
        this.markUpdated();
    }

    public void softDelete() {
        this.isDeleted = true;
        this.markUpdated();
    }

    public void updateProfile(String newNickname){
        this.nickname = newNickname;
        this.markUpdated();
    }

    public void updateProfileImgUrl(String newImgUrl){
        this.filesUserProfileImgUrl.setImgUrl(newImgUrl);
        this.markUpdated();
    }
}
