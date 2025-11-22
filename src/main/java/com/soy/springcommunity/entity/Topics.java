package com.soy.springcommunity.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access= AccessLevel.PROTECTED)
@Entity
@Table(name = "topics")
public class Topics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "topic", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Posts> posts;

    @Column(name = "code", length = 10, nullable = false)
    private String code;

    @Column(name = "label", length= 26, nullable = false)
    private String label;

    @Column(name = "post_counts")
    private Long postCounts;

    public void increasePostCounts(){
        this.postCounts++;
    }

    public void decreasePostCounts(){
        this.postCounts--;
    }
}
