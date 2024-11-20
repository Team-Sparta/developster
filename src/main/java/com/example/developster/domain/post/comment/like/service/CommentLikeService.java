package com.example.developster.domain.post.comment.like.service;

import com.example.developster.domain.post.comment.like.repository.CommentLikeRepository;
import com.example.developster.domain.user.main.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository likeRepository;

    public void likeComment(Long commentId, User user) {
    }

    public void unlikeComment(Long commentId, User user) {
    }
}
