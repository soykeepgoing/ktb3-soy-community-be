package com.soy.springcommunity.repository.likes;

import com.soy.springcommunity.entity.PostLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostLikesRepository extends JpaRepository<PostLikes, Long> {
    boolean existsByPostIdAndUserId(Long postId, Long userId);
    Optional<PostLikes> findByPostIdAndUserIdAndDeletedAtIsNull(Long postId, Long userId);
}
