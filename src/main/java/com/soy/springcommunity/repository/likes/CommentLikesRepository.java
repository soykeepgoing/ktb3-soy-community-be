package com.soy.springcommunity.repository.likes;

import com.soy.springcommunity.entity.CommentLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikesRepository extends JpaRepository<CommentLikes, Long> {
    boolean existsByCommentIdAndUserId(Long commentId, Long userId);
    Optional<CommentLikes> findByCommentIdAndUserIdAndDeletedAtIsNull(Long commentId, Long userId);
}
