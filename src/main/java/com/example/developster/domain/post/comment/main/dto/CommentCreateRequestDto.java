package com.example.developster.domain.post.comment.main.dto;

import com.example.developster.domain.post.comment.main.entity.Comment;
import lombok.Getter;

@Getter
public class CommentCreateRequestDto {
    private final Long parentId;
    private final String contents;

    public CommentCreateRequestDto(Comment comment){
        this.parentId = comment.getParentComment().getId();
        this.contents = comment.getContents();
    }
}
