package com.example.developster.domain.post.comment.like.repository;

import com.example.developster.domain.post.comment.like.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

}
