package com.example.developster.domain.notification.enums;

public enum NotificationType {

    POST_LIKE("게시물 종아요"), COMMENT_LIKE("댓글 종아요"), COMMENT("댓글"), FOLLOW("팔로우");

    private final String type;

    NotificationType(String type) {
        this.type = type;
    }
}
