package com.example.developster.domain.post.main.dto.response;

import com.example.developster.domain.post.main.dto.PostDetailInfo;
import com.example.developster.domain.post.media.dto.MediaInfo;

public record PostResponseDto(
        PostDetailInfo postDetailInfo,
        MediaInfo mediaInfo
) {
}