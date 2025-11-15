package com.soy.springcommunity.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "files_posts_img_url")
public class FilesPostImgUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "img_url", nullable = false)
    private String imgUrl;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name="post_id", unique = true, nullable = false)
    private Posts post;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private FilesPostImgUrl(Posts post, String imgUrl) {
        this.post = post;
        this.imgUrl = imgUrl;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
        this.deletedAt = null;
    }

    public static FilesPostImgUrl of(Posts post, String imgUrl) {
        return new FilesPostImgUrl(post, imgUrl);
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}

