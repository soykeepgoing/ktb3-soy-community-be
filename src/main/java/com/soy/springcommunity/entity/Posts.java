package com.soy.springcommunity.entity;

import com.soy.springcommunity.entity.Comments;
import com.soy.springcommunity.entity.Users;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "posts")
@NamedEntityGraphs({
        @NamedEntityGraph(
                name = "Posts.withUserAndStats",
                attributeNodes = {
                        @NamedAttributeNode("user"),
                        @NamedAttributeNode("postStats")
                }
        ),
        @NamedEntityGraph(
                name = "Posts.withUserAndStatsAndComments",
                attributeNodes = {
                        @NamedAttributeNode("user"),
                        @NamedAttributeNode("postStats"),
                        @NamedAttributeNode("comments")
                }
        )
    }
)
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    // @OneToMany(mappedBy = "post")
    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Comments> comments;

    @OneToOne(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private PostStats postStats;

    @OneToOne(mappedBy = "post", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private FilesPostImgUrl filesPostImgUrl;

    @Column(name = "title", length = 26, nullable = false)
    private String title;
    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Posts(String title, String body, Users user) {
        this.title = title;
        this.body = body;
        this.user = user;
        this.createdAt = LocalDateTime.now();
    }

    public void updatePostId(Long postId) {
        this.id = postId;
    }

    public void updatePostTitle(String title) {
        if (title != null & title != "") {
            this.title = title;
        }
    }

    public void updatePostContent(String content) {
        if (content != null & content != "") {
            this.body = content;
        }
    }

    public void updateModifiedAt(){
        this.updatedAt = LocalDateTime.now();
    }

    public void updatePostImage(String url){
        this.filesPostImgUrl.setImgUrl(url);
        updateModifiedAt();
    }
}