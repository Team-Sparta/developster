package com.example.developster.domain.post.like.repository;

import com.example.developster.domain.post.like.entity.PostLike;
import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.user.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostLikeJpaRepository extends JpaRepository<PostLike, Long> {
    Optional<PostLike> findByUserAndPost(User user, Post post);
}
