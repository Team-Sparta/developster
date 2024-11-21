package com.example.developster.domain.post.main.repository;

import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostJpaRepository extends JpaRepository<Post, Long> {
    default Post fetchPost(Long postId) {
        return findById(postId).orElseThrow(() -> new InvalidParamException(ErrorCode.NOT_FOUND_POST));
    }
}
