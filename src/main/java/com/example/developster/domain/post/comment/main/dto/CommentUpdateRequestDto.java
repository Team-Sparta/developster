package com.example.developster.domain.post.comment.main.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class CommentUpdateRequestDto {

    @NotBlank
    @Size(max = 200)
    private final String contents;

    public CommentUpdateRequestDto(@NotBlank @Size(max = 200) String contents) {
        this.contents = contents;

    }
}
