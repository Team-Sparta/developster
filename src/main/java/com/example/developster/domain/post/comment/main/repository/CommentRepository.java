package com.example.developster.domain.post.comment.main.service;

import com.example.developster.domain.post.comment.main.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Long, Comment> {
}
