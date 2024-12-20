package com.example.developster.domain.post.comment.main.dto.response;

import lombok.Getter;

@Getter
public class CommentUpdateResponseDto {
    private final Long commentId;

    public CommentUpdateResponseDto(Long commentId) {
        this.commentId = commentId;
    }
}
