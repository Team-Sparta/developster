package com.example.developster.domain.post.comment.main.dto;

import com.example.developster.domain.post.comment.main.entity.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentCreateRequestDto {

    private final Long parentId;

    @NotBlank
    @Size(max = 200)
    private final String contents;

    public CommentCreateRequestDto(Long parentId, @NotBlank @Size(max = 200) String contents){
        this.parentId = parentId;
        this.contents = contents;
    }
}
