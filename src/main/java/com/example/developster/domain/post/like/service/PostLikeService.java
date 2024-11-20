package com.example.developster.domain.post.like.service;

import com.example.developster.domain.post.like.entity.PostLike;
import com.example.developster.domain.post.like.repository.PostLikeJpaRepository;
import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.post.main.repository.PostJpaRepository;
import com.example.developster.domain.user.main.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeJpaRepository postLikeJpaRepository;
    private final PostJpaRepository postJpaRepository;

    public void likePost(User user, Long postId) {
        Post post = postJpaRepository.findByIdOrElseThrow(postId);
        Optional<PostLike> postLike = postLikeJpaRepository.findByUserAndPost(user, post);

        postLike.map(like -> {
            postLikeJpaRepository.delete(like);
            return false;
        }).orElseGet(() -> {
            PostLike newPostLike = PostLike.create(user, post);
            postLikeJpaRepository.save(newPostLike);
            return true;
        });
    }

    public void unlikePost(User user, Long postId) {

    }
}
