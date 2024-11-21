package com.example.developster.domain.post.main.dto;

import com.example.developster.domain.post.media.dto.MediaInfo;
import com.example.developster.domain.user.main.dto.UserInfoDto;

import java.time.LocalDateTime;

public record PostSummary(
        Long postId,
        String title,
        String content,
        Long likeCount,
        Long commentCount,
        Boolean isLiked,
        Boolean isPrivate,
        LocalDateTime createdAt,
        MediaInfo mediaInfo,
        UserInfoDto author
) {

}