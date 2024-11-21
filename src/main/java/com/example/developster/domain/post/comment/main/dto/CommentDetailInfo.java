package com.example.developster.domain.post.comment.main.dto;

import com.example.developster.domain.user.main.dto.UserInfoDto;

public record CommentDetailInfo(
        Long postId,
        Long commentId,
        String contents,
        long likeCount,
        long commentCount,
        boolean isLiked,
        UserInfoDto userInfo) {

}
