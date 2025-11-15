package com.soy.springcommunity.repository.comments;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.soy.springcommunity.entity.Comments;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentsRepository extends JpaRepository<Comments, Long> {
    Optional<Comments> findById(Long id);
    List<Comments> findByPostId(Long postId);
}
