package com.soy.springcommunity.entity;

import jakarta.persistence.*;
import lombok.*;


@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Table(name = "post_stats")
public class PostStats {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name="post_id", unique = true, nullable = false)
    private Posts post;

    @Column(name = "view_count", nullable = false)
    private Long viewCount;
    @Column(name = "like_count", nullable = false)
    private Long likeCount;
    @Column(name = "comment_count", nullable = false)
    private Long commentCount;

    private PostStats(Posts post, Long viewCount, Long likeCount, Long commentCount) {
        this.post = post;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.commentCount = commentCount;
    }

    public static PostStats createStats(Posts post) {
        return new PostStats(
                post,
                0L,
                0L,
                0L
        );
    }

    public void increaseViewCount(){
        this.viewCount += 1;
    }

    public Long increaseLikeCount(){
        this.likeCount += 1;
        return this.likeCount;
    }

    public Long decreaseLikeCount(){
        this.likeCount -= 1;
        return this.likeCount;
    }

    public void increaseCommentCount(){
        this.commentCount += 1;
    }

    public void decreaseCommentCount(){
        this.commentCount -= 1;
    }

}
