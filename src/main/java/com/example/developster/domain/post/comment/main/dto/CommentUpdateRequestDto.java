package com.example.developster.domain.post.comment.main.dto;

import lombok.Getter;

@Getter
public class CommentUpdateRequestDto {
    private final String contents;

    public CommentUpdateRequestDto(String contents) {
        this.contents = contents;

    }
}
