package com.soy.springcommunity.service;

import com.soy.springcommunity.dto.*;
import com.soy.springcommunity.entity.CommentStats;
import com.soy.springcommunity.entity.Comments;
import com.soy.springcommunity.entity.Posts;
import com.soy.springcommunity.entity.Users;
import com.soy.springcommunity.exception.CommentsException;
import com.soy.springcommunity.exception.PostsException;
import com.soy.springcommunity.exception.UsersException;
import com.soy.springcommunity.repository.comments.CommentsRepository;
import com.soy.springcommunity.repository.comments.CommentsStatsRepository;
import com.soy.springcommunity.repository.posts.PostsRepository;
import com.soy.springcommunity.repository.users.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    private PostsRepository postsRepository;
    private UsersRepository usersRepository;
    private CommentsRepository commentsRepository;
    private CommentsStatsRepository commentsStatsRepository;

    @Autowired
    public CommentsService(CommentsRepository commentsRepository,
                           UsersRepository usersRepository,
                           PostsRepository postsRepository,
                           CommentsStatsRepository commentsStatsRepository) {
        this.commentsRepository = commentsRepository;
        this.usersRepository = usersRepository;
        this.postsRepository = postsRepository;
        this.commentsStatsRepository = commentsStatsRepository;
    }

    public void ensureCommentMatchPost(Long commentPostId, Long postId) {
        if (!commentPostId.equals(postId)) {
            throw new CommentsException.CommentsNotMatchPostException("댓글 정보를 확인하세요.");
        }
    }

    public void ensureCommentMatchUser(Long commentUserId, Long userId) {
        if(!commentUserId.equals(userId)){
            throw new CommentsException.CommentsUnauthorizedException("댓글에 대한 권한이 없습니다." + commentUserId + userId);
        }

    }

    @Transactional
    public List<CommentsItemResponse> viewComments(Long postId) {
        List<CommentsItemResponse> commentsItemResponseList = commentsRepository.findByPostId(postId).stream().map(CommentsItemResponse::from).toList();
        return commentsItemResponseList;
    }

    @Transactional
    public CommentsCreateResponse createComments(CommentsCreateRequest createCommentRequest,
                                                 Long postId,
                                                 Long userId,
                                                 Long parentCommentId
    ) {
        Users user = usersRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(()-> new UsersException.UsersNotFoundException("존재하지않는 유저입니다."));

        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new PostsException.PostsNotFoundException("존재하지 않는 게시글입니다."));

        Comments parentComments = null;
        if(parentCommentId != null){
            parentComments = commentsRepository.findById(parentCommentId)
                    .orElseThrow(()-> new CommentsException.CommentsNotFoundException("존재하지 않는 댓글입니다."));
        }

        Comments comments = Comments.builder()
                .body(createCommentRequest.getCommentContent())
                .user(user)
                .posts(post)
                .parentComments(parentComments)
                .build();

        CommentStats commentStats = CommentStats.createStats(comments);
        post.getPostStats().increaseCommentCount();

        commentsRepository.save(comments);
        commentsStatsRepository.save(commentStats);

        return CommentsCreateResponse.of(
                comments.getId(),
                comments.getBody(),
                comments.getUser().getFilesUserProfileImgUrl().getImgUrl(),
                comments.getUser().getNickname(),
                comments.getCreatedAt()
        );
    }

    public void editCommentBody(Comments comments, String newBody) {
        comments.updateCommentBody(newBody);
    }

    @Transactional
    public SimpleResponse editComments(CommentsEditRequest editCommentRequest,
                                       Long postId,
                                       Long commentId,
                                       Long userId){
        Comments comment = commentsRepository.findById(commentId)
                        .orElseThrow(() -> new CommentsException.CommentsNotFoundException("존재하지 않는 댓글입니다."));

        ensureCommentMatchUser(comment.getUser().getId(), userId);
        ensureCommentMatchPost(comment.getPost().getId(), postId);
        editCommentBody(comment, editCommentRequest.getNewCommentContent());
        return SimpleResponse.forEditComment(userId, commentId);
    }

    @Transactional
    public SimpleResponse deleteComments(Long postId, Long commentId, Long userId){
        Comments comment = commentsRepository.findById(commentId)
                .orElseThrow(() -> new CommentsException.CommentsNotFoundException("존재하지 않는 댓글입니다."));

        ensureCommentMatchUser(comment.getUser().getId(), userId);
        ensureCommentMatchPost(comment.getPost().getId(), postId);
        comment.getPost().getPostStats().decreaseCommentCount();
        commentsRepository.delete(comment);
        return SimpleResponse.forDeleteComment(userId, commentId);
    }


}
