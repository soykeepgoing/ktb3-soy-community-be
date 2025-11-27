package com.soy.springcommunity.entity;

import jakarta.persistence.*;
import org.springframework.util.StringUtils;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
@NamedEntityGraph(
        name = "Posts.withUserAndStats",
        attributeNodes = {
                @NamedAttributeNode("user"),
                @NamedAttributeNode("postStats")
        }
)
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topics topic;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comments> comments;

    @OneToOne(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private PostStats postStats;

    @OneToOne(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private FilesPostImgUrl filesPostImgUrl;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Posts(String content, Topics topic, Users user) {
        this.content = content;
        this.topic = topic;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public void updatePostId(Long postId) {
        this.id = postId;
    }

    public void updatePostContent(String content) {
        if (StringUtils.hasText(content)) {
            this.content = content;
        }
    }

    public void updateModifiedAt(){
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePostImage(String url){
        this.filesPostImgUrl.updateImgUrl(url);
        updateModifiedAt();
    }
}