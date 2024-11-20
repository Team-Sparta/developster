package com.example.developster.domain.notification.enums;

public enum NotificationType {

    LIKE("종아요"), COMMENT("댓글"), FOLLOW("팔로우"), MESSAGE("메세지");

    private final String type;

    NotificationType(String type) {
        this.type = type;
    }
}
