package com.soy.springcommunity.service;

import com.soy.springcommunity.dto.*;
import com.soy.springcommunity.entity.FilesPostImgUrl;
import com.soy.springcommunity.entity.PostStats;
import com.soy.springcommunity.entity.Posts;
import com.soy.springcommunity.entity.Users;
import com.soy.springcommunity.exception.PostsException;
import com.soy.springcommunity.exception.UsersException;
import com.soy.springcommunity.repository.files.FilesPostImgRepository;
import com.soy.springcommunity.repository.likes.PostLikesRepository;
import com.soy.springcommunity.repository.posts.PostsRepository;
import com.soy.springcommunity.repository.posts.PostsStatsReposiotry;
import com.soy.springcommunity.repository.users.UsersRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.soy.springcommunity.utils.ConstantUtil.URL_DEFAULT_POST_IMG;

@Service
public class PostsService {
    @PersistenceContext
    private EntityManager entityManager;

    private PostsRepository postsRepository;
    private UsersRepository usersRepository;
    private PostsStatsReposiotry postsStatsReposiotry;
    private PostLikesRepository postLikesRepository;
    private FilesPostImgRepository filesPostImgRepository;

    @Autowired
    public PostsService(PostsRepository postsRepository,
                        UsersRepository usersRepository,
                        PostsStatsReposiotry postsStatsReposiotry,
                        PostLikesRepository postLikesRepository,
                        FilesPostImgRepository filesPostImgRepository) {
        this.postsRepository = postsRepository;
        this.usersRepository = usersRepository;
        this.postsStatsReposiotry = postsStatsReposiotry;
        this.filesPostImgRepository = filesPostImgRepository;
        this.postLikesRepository = postLikesRepository;
    }

    @Transactional
    public PostsListResponse viewPostList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Posts> postList = postsRepository.findAll(pageable);
        List<PostsItemResponse> postsItemResponseList = postList.stream().map(PostsItemResponse::from).toList();

        PostsPagingMetaResponse pagingMetaResponse = PostsPagingMetaResponse.builder()
                .pageNumber(page)
                .pageSize(size)
                .sortCondition("createdAt,desc")
                .build();

        return new PostsListResponse(
                postsItemResponseList,
                pagingMetaResponse
        );
    }

    @Transactional
    public PostsDetailResponse viewPostDetail(Long postId, Long userId) {
        Posts post = postsRepository.findById(postId)
                .orElseThrow(() -> new PostsException.PostsNotFoundException("존재하지 않는 게시글입니다."));
        post.getPostStats().increaseViewCount();
        Boolean isUserLiked = postLikesRepository.existsByPostIdAndUserId(postId, userId);
        return PostsDetailResponse.of(post, isUserLiked);
    }

    @Transactional
    public PostsCreateResponse createPost(Long userId, PostsCreateRequest postsCreateRequest) {
        Users user = usersRepository.findByIdAndIsDeletedFalse(userId)
                .orElseThrow(()-> new UsersException.UsersNotFoundException("존재하지않는 유저입니다."));

        Posts post = Posts.builder()
                .title(postsCreateRequest.getPostTitle())
                .body(postsCreateRequest.getPostContent())
                .user(user).build();

        PostStats postStats = PostStats.createStats(post);

        FilesPostImgUrl filesPostImgUrl = FilesPostImgUrl.of(post, URL_DEFAULT_POST_IMG);

        postsRepository.save(post);
        postsStatsReposiotry.save(postStats);
        filesPostImgRepository.save(filesPostImgUrl);

        return PostsCreateResponse.of(post.getId());

    }

    public void ensureUserIsPostWriter(Long postWriterId, Long userId){
        if (postWriterId != userId){
            throw new PostsException.PostsNotAuthorizedException("접근할 수 없는 게시글입니다.");
        }
    }

    public void validatePostEditRequest(PostsEditRequest postEditRequest) {
        if((postEditRequest.getPostTitle()==null || postEditRequest.getPostTitle().isEmpty()) &&
                (postEditRequest.getPostContent()==null || postEditRequest.getPostContent().isEmpty()) &&
                (postEditRequest.getPostImageUrl()==null || postEditRequest.getPostImageUrl().isEmpty())){
            throw new PostsException.NoEditPostsException("수정할 내용이 없습니다.");
        }
    }

    public void editPostTitle(Posts posts, String newTitle) {
        posts.updatePostTitle(newTitle);
    }

    public void editPostContent(Posts posts, String newContent) {
        posts.updatePostContent(newContent);
    }

    @Transactional
    public SimpleResponse editPost(Long postId, Long userId, PostsEditRequest postEditRequest) {
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new PostsException.PostsNotFoundException("존재하지 않는 게시글입니다."));
        ensureUserIsPostWriter(posts.getUser().getId(), userId);
        validatePostEditRequest(postEditRequest);
        editPostTitle(posts, postEditRequest.getPostTitle());
        editPostContent(posts, postEditRequest.getPostContent());
//        editPostImgUrl(posts, postEditRequest.getPostImageUrl());
        posts.updateModifiedAt();
        return SimpleResponse.forEditPost(userId, postId);
    }

    @Transactional
    public SimpleResponse deletePost(Long postId, Long userId) {
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new PostsException.PostsNotFoundException("존재하지 않는 게시글입니다."));
        ensureUserIsPostWriter(posts.getUser().getId(), userId);
        postsRepository.deleteById(postId);
        return SimpleResponse.forDeletePost(userId, postId);
    }

    @Transactional
    public void updatePostImage(Long postId, String url){
        Posts posts = postsRepository.findById(postId)
                .orElseThrow(() -> new PostsException.PostsNotFoundException("존재하지 않는 게시글입니다."));
        posts.updatePostImage(url);
    }
}
