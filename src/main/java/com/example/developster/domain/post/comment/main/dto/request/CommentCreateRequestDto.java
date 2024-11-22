package com.example.developster.domain.post.comment.main.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class CommentCreateRequestDto {
    @Schema(description = "부모 댓글 아이디")
    private final Long parentId;

    @Schema(description = "댓글 내용")
    @NotBlank
    @Size(max = 200)
    private final String contents;

    public CommentCreateRequestDto(Long parentId, @NotBlank @Size(max = 200) String contents){
        this.parentId = parentId;
        this.contents = contents;
    }
}
