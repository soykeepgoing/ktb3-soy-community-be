package com.soy.springcommunity.entity;

import jakarta.persistence.*;
import lombok.*;

import javax.xml.stream.events.Comment;
import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comments")
@NamedEntityGraphs({
    @NamedEntityGraph(
            name = "Comments.withPosts",
            attributeNodes = {
                    @NamedAttributeNode("post")
            }
    ),
    @NamedEntityGraph(
            name = "Comments.withUsers",
            attributeNodes = {
                    @NamedAttributeNode("user")
            }
    )
})

public class Comments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id", nullable = true)
    private Comments parentComment;

    @OneToMany(mappedBy = "parentComment")
    private List<Comments> childrenCommentList;

    @OneToOne(mappedBy = "comment", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private CommentStats commentStats;

    @Column(name = "body", nullable = false)
    private String body;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "comment_depth", nullable = true)
    private int commentDepth;

    @Builder
    public Comments(String body, Users user, Posts posts, Comments parentComments){
        this.body = body;
        this.user = user;
        this.post = posts;
        this.parentComment = parentComments;
        this.createdAt = LocalDateTime.now();
    }

    public void updateCommentBody(String newBody) {
        this.body = newBody;
    }
}

