package com.soy.springcommunity.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseLikes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private Users user;

    @Column(name = "created_at", nullable=false)
    protected LocalDateTime createdAt;
    @Column(name = "deleted_at")
    protected LocalDateTime deletedAt;

    public BaseLikes(Users user) {
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public void deleteLikes(){
        this.deletedAt = LocalDateTime.now();
    }
}
