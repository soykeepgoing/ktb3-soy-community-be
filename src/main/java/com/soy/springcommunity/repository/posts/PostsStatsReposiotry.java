package com.soy.springcommunity.repository.posts;

import com.soy.springcommunity.entity.PostStats;
import com.soy.springcommunity.entity.Posts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsStatsReposiotry extends JpaRepository<PostStats, Long> {
}
