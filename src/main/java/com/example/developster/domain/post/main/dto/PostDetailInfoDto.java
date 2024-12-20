package com.example.developster.domain.post.main.dto;

import com.example.developster.domain.user.main.dto.UserInfoDto;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record PostDetailInfoDto(
        Long postId,
        String title,
        String content,
        UserInfoDto userInfo,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "Asia/Seoul")
        LocalDateTime createdAt,
        long likeCount,
        long commentCount,
        boolean isLiked,
        boolean isPrivate
) {
}
