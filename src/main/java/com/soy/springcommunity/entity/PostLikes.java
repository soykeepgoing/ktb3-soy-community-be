package com.soy.springcommunity.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_likes")
@NamedEntityGraph(
        name = "PostLikes.withPostAndUser",
        attributeNodes = {
                @NamedAttributeNode("post"),
                @NamedAttributeNode("user")
        }
)
public class PostLikes extends BaseLikes{
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private Posts post;

    private PostLikes(Posts post, Users user) {
        super(user);
        this.post = post;
    }

    public static PostLikes of(Posts post, Users user) {
        return new PostLikes(post, user);
    }

}
