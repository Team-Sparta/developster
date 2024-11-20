package com.example.developster.domain.post.bookmark.repository;

import com.example.developster.domain.post.bookmark.entity.PostBookmark;

import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.user.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostBookmarkJpaRepository extends JpaRepository<PostBookmark, Long> {
    Optional<PostBookmark> findByUserAndPost(User user, Post post);
}
