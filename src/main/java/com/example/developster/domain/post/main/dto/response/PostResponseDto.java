package com.example.developster.domain.post.main.dto.response;

import com.example.developster.domain.post.main.dto.PostDetailInfoDto;
import com.example.developster.domain.post.media.dto.MediaInfo;

public record PostResponseDto(
        PostDetailInfoDto postDetailInfo,
        MediaInfo mediaInfo
) {
}