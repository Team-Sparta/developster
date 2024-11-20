package com.example.developster.domain.post.comment.main.dto;

import lombok.Getter;

@Getter
public class CommentReadResDto {
    private final Long postId;
    private final Long commentId;
    private final String contents;

    public CommentReadResDto(Long postId, Long commentId, String contents) {
        this.postId = postId;
        this.commentId = commentId;
        this.contents = contents;
    }
}
