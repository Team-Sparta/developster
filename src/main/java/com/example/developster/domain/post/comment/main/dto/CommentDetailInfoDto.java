package com.example.developster.domain.post.comment.main.dto;

import com.example.developster.domain.user.main.dto.UserInfoDto;

public record CommentDetailInfoDto(
        Long postId,
        Long commentId,
        String contents,
        long likeCount,
        long commentCount,
        boolean isLiked,
        UserInfoDto userInfo
){ }
