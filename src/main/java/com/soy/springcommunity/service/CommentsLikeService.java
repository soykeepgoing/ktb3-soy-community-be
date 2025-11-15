package com.soy.springcommunity.service;

import com.soy.springcommunity.dto.LikesSimpleResponse;
import com.soy.springcommunity.entity.*;
import com.soy.springcommunity.exception.CommentsException;
import com.soy.springcommunity.exception.LikesException;
import com.soy.springcommunity.exception.UsersException;
import com.soy.springcommunity.repository.comments.CommentsRepository;
import com.soy.springcommunity.repository.likes.CommentLikesRepository;
import com.soy.springcommunity.repository.users.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentsLikeService implements LikesService {
    private CommentsRepository commentsRepository;
    private CommentLikesRepository commentLikesRepository;
    private UsersRepository usersRepository;

    @Autowired
    public CommentsLikeService(
            CommentLikesRepository commentLikesRepository,
            CommentsRepository commentsRepository,
            UsersRepository usersRepository) {
        this.commentLikesRepository = commentLikesRepository;
        this.commentsRepository = commentsRepository;
        this.usersRepository = usersRepository;
    }

    @Transactional
    @Override
    public LikesSimpleResponse like(Long contentId, Long userId) {
        if (commentLikesRepository.existsByCommentIdAndUserId(contentId, userId)) {
            throw new LikesException.AlreadyLikedException("이미 좋아요한 댓글입니다.");
        }

        Comments comments = commentsRepository.findById(contentId)
                .orElseThrow(() -> new CommentsException.CommentsNotFoundException("존재하지 않는 댓글입니다."));

        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new UsersException.UsersNotFoundException("존재하지 않는 사용자입니다."));

        CommentLikes commentLikes = CommentLikes.of(comments, users);
        Long likeCounts = comments.getCommentStats().increaseLikeCount();
        commentLikesRepository.save(commentLikes);
        return LikesSimpleResponse.forLike(
                "comment",
                contentId,
                userId,
                likeCounts
        );
    }

    @Transactional
    @Override
    public LikesSimpleResponse unlike(Long contentId, Long userId) {
        CommentLikes checkCommentLikes = commentLikesRepository.findByCommentIdAndUserIdAndDeletedAtIsNull(contentId, userId)
                .orElseThrow(()-> new LikesException.AlreadyLikedException("이미 좋아요한 게시글입니다."));
        checkCommentLikes.deleteLikes();
        Long likeCounts = checkCommentLikes.getComment().getCommentStats().decreaseLikeCount();
        return LikesSimpleResponse.forUnlike(
                "comment",
                contentId,
                userId,
                likeCounts
        );
    }
}
