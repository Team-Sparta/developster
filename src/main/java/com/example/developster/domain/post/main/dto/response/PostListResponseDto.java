package com.example.developster.domain.post.main.dto.response;

import org.springframework.data.domain.Slice;

public record PostListResponseDto(
        Slice<PostResponseDto> posts
) {
}
