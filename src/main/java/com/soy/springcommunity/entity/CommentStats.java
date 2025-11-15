package com.soy.springcommunity.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comment_stats")
public class CommentStats {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "comment_id", unique = true, nullable = false)
    private Comments comment;

    @Column(name = "like_count", nullable = false)
    private Long likeCount;
    @Column(name = "reply_count", nullable = false)
    private Long replyCount;

    private CommentStats(Comments comment, Long likeCount, Long replyCount) {
        this.comment = comment;
        this.likeCount = likeCount;
        this.replyCount = replyCount;
    }

    public static CommentStats createStats(Comments comment) {
        return new CommentStats(comment, 0l, 0l);
    }

    public Long increaseLikeCount() {
        this.likeCount++;
        return likeCount;
    }

    public Long decreaseLikeCount() {
        this.likeCount--;
        return likeCount;
    }

}

