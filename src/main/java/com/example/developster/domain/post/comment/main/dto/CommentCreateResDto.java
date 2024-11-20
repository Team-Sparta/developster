package com.example.developster.domain.post.comment.main.dto;


import com.example.developster.domain.post.comment.main.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentCreateResDto {
    private final Long postId;
    private final Long parentId;
    private final Long commentId;
    private final String contents;
    private final String writer;
    private final LocalDateTime createAt;


    public CommentCreateResDto(Long postId, Long parentId, Long commentId, String contents, String writer,LocalDateTime createAt) {
        this.postId = postId;
        this.parentId = parentId;
        this.commentId = commentId;
        this.contents = contents;
        this.writer = writer;
        this.createAt = createAt;
    }

    public CommentCreateResDto(Comment comment){
        this.postId = comment.getPost().getId();
        this.parentId = comment.getComment().getId();
        this.commentId = comment.getId();
        this.contents = comment.getContents();
        this.writer = comment.getUser().getName();
        this.createAt = comment.getCreatedAt();
    }
}
