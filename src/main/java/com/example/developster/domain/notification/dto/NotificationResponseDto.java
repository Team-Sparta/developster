package com.example.developster.domain.notification.dto;


import com.example.developster.domain.notification.entity.Notification;
import com.example.developster.domain.notification.enums.NotificationType;

import java.time.LocalDateTime;

public class NotificationResponseDto {

    private final Long id;
    private final String message;
    private final Boolean isRead;
    private final NotificationType type;
    private final LocalDateTime createdAt;
    private final String relatedUrl;

    public NotificationResponseDto(Long id, String message, Boolean isRead, NotificationType type, LocalDateTime createdAt, String relatedUrl) {
        this.id = id;
        this.message = message;
        this.isRead = isRead;
        this.type = type;
        this.createdAt = createdAt;
        this.relatedUrl = relatedUrl;
    }


    public static NotificationResponseDto toDto(Notification notification) {
        return new NotificationResponseDto(notification.getNotificationId(), notification.getMessage(), notification.getIsRead(),
                                            notification.getType(), notification.getCreatedAt(), notification.getRelatedUrl());
    }
}
