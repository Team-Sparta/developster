package com.example.developster.domain.post.comment.main.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class CommentUpdateRequestDto {

    @Schema(description = "수정할 댓글 내용")
    @NotBlank
    @Size(max = 200)
    private final String contents;

    public CommentUpdateRequestDto(@NotBlank @Size(max = 200) String contents) {
        this.contents = contents;

    }
}
