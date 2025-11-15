package com.soy.springcommunity.repository.comments;

import com.soy.springcommunity.entity.CommentStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsStatsRepository extends JpaRepository<CommentStats, Long> {
}
