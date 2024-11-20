package com.example.developster.domain.post.comment.main.dto;

import lombok.Getter;

@Getter
public class CommentUpdateReqDto {
    private final String contents;

    public CommentUpdateReqDto(String contents) {
        this.contents = contents;

    }
}
