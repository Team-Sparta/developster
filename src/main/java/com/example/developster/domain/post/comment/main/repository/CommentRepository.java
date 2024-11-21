package com.example.developster.domain.post.comment.main.repository;

import com.example.developster.domain.post.comment.main.entity.Comment;
import com.example.developster.global.exception.BaseException;
import com.example.developster.global.exception.code.ErrorCode;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    default Comment findByIdOrElseThrow(Long id){
        if(id == null){
            return null;
        }
        return findById(id).orElseThrow(() -> new BaseException(ErrorCode.NOT_FOUND_COMMENT));
    }

    // 마지막 ID를 기준으로 이후 최근 데이터를 조회 (keyset pagination)
    @Query("SELECT c FROM Comment c WHERE c.post.id = :postId AND c.parentComment.id IS NULL AND c.deletedAt IS NULL AND c.id < :lastId ORDER BY c.createdAt DESC")
    List<Comment> readComments(@Param("postId") Long postId, @Param("lastId") Long lastId, Pageable pageable);

    @Query("SELECT c FROM Comment c WHERE c.parentComment.id = :commentId AND c.deletedAt IS NULL AND c.id < :lastId ORDER BY c.createdAt DESC")
    List<Comment> readReplies(Long commentId, Long lastId, Pageable pageable);


}
