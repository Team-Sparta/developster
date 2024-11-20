package com.example.developster.domain.post.comment.like.service;

import com.example.developster.domain.post.comment.like.entity.CommentLike;
import com.example.developster.domain.post.comment.like.repository.CommentLikeRepository;
import com.example.developster.domain.post.comment.main.entity.Comment;
import com.example.developster.domain.post.comment.main.repository.CommentRepository;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.exception.BaseException;
import com.example.developster.global.exception.code.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final CommentLikeRepository likeRepository;
    private final CommentRepository commentRepository;
    public void likeComment(Long commentId, User user) {
        //로그인 회원이 다른 사람의 댓글에 좋아요를 누른다.

        Comment comment = commentRepository.findByIdOrElseThrow(commentId);

        //내 댓글에는 좋아요 못누름!
        if(Objects.equals(comment.getUser().getId(), user.getId())){
            throw new BaseException(ErrorCode.CONSTRAINT_VIOLATION);
        }

        //좋아요 생성
        CommentLike like = CommentLike.builder()
                .user(user)
                .comment(comment)
                .build();

        likeRepository.save(like);
    }

    public void unlikeComment(Long commentId, User user) {
        //로그인 한 회원이 다른 사람의 댓글에 좋아요 취소(삭제)
        Comment comment = commentRepository.findByIdOrElseThrow(commentId);
        //내 댓글에는 취소 못누름!
        if(Objects.equals(comment.getUser().getId(), user.getId())){
            throw new BaseException(ErrorCode.CONSTRAINT_VIOLATION);
        }

        likeRepository.findByComment_IdAndUser_IdOrElseThrow(commentId,user.getId());
    }
}
