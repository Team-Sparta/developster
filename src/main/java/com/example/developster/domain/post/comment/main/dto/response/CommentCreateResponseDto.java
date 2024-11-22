package com.example.developster.domain.post.comment.main.dto.response;


import com.example.developster.domain.post.comment.main.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentCreateResponseDto {
    private final Long postId;
    private final Long parentId;
    private final Long commentId;
    private final String contents;
    private final String writer;
    private final LocalDateTime createAt;


    public CommentCreateResponseDto(Comment comment){
        this.postId = comment.getPost().getId();
        this.parentId = comment.getParentComment() == null ? null : comment.getParentComment().getId();
        this.commentId = comment.getId();
        this.contents = comment.getContents();
        this.writer = comment.getUser().getName();
        this.createAt = comment.getCreatedAt();
    }
}
