package com.soy.springcommunity.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "user_details")
public class UserDetail {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private Users user;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private UserDetail(Users user){
        this.user = user;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
        this.deletedAt = null;
    }

    public static UserDetail of(Users user) {
        UserDetail userDetail = new UserDetail(user);
        userDetail.setUser(user);
        return userDetail;
    }

    public void setUser(Users user) {
        this.user = user;
        if (user.getUserDetail() != this) {
            user.setUserDetail(this);
        }
    }

    public void setDeletedAt(){
        this.deletedAt = LocalDateTime.now();
    }

    public void setUpdatedAt(){
        this.updatedAt = LocalDateTime.now();
    }
}
