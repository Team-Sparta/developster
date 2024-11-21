package com.example.developster.domain.post.like.service;

import com.example.developster.domain.post.like.entity.PostLike;
import com.example.developster.domain.post.like.repository.PostLikeJpaRepository;
import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.post.main.repository.PostJpaRepository;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.exception.BaseException;
import com.example.developster.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostLikeJpaRepository postLikeJpaRepository;
    private final PostJpaRepository postJpaRepository;

    public void likePost(User user, Long postId) {
        Post post = postJpaRepository.findByIdOrElseThrow(postId);
        Optional<PostLike> postLike = postLikeJpaRepository.findByUserAndPost(user, post);

        if (postLike.isPresent()) {
            throw new BaseException(ErrorCode.ALREADY_LIKED_POST);
        }

        PostLike newPostLike = PostLike.create(user, post);
        postLikeJpaRepository.save(newPostLike);
    }

    public void unlikePost(User user, Long postId) {
        Post post = postJpaRepository.findByIdOrElseThrow(postId);
        Optional<PostLike> postLike = postLikeJpaRepository.findByUserAndPost(user, post);

        if (postLike.isEmpty()) {
            throw new BaseException(ErrorCode.NOT_FOUND_POST_LIKE);
        }
        postLikeJpaRepository.delete(postLike.get());
    }
}
