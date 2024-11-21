package com.example.developster.domain.post.comment.like.repository;

import com.example.developster.domain.post.comment.like.entity.CommentLike;
import com.example.developster.global.exception.BaseException;
import com.example.developster.global.exception.code.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    Optional<CommentLike> findByComment_IdAndUser_Id(Long commentId, Long userId);


    default CommentLike findByComment_IdAndUser_IdOrElseThrow(Long commentId, Long userId){
        return findByComment_IdAndUser_Id(commentId, userId).orElseThrow(
                () -> new BaseException(ErrorCode.NOT_FOUND_COMMENT_LIKE)
        );
    }
}
