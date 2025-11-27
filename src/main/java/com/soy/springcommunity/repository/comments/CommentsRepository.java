package com.soy.springcommunity.repository.comments;

import jakarta.persistence.Entity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.soy.springcommunity.entity.Comments;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Optional<Comments> findById(Long id);
    @EntityGraph(attributePaths = {"user", "user.filesUserProfileImgUrl", "commentStats"})
    List<Comments> findByPostId(Long postId);
}
