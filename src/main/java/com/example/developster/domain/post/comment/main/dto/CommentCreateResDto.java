package com.example.developster.domain.post.comment.main.dto;


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
}
