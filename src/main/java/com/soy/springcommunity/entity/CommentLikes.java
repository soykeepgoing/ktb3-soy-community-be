package com.soy.springcommunity.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comment_likes")
@NamedEntityGraph(
        name = "CommentLikes.withCommentAndUser",
        attributeNodes = {
                @NamedAttributeNode("comment"),
                @NamedAttributeNode("user")
        }
)
public class CommentLikes extends BaseLikes {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id")
    private Comments comment;

    private CommentLikes(Comments comment, Users user) {
        super(user);
        this.comment = comment;
    }

    public static CommentLikes of(Comments comment, Users user) {
        return new CommentLikes(comment, user);
    }
}