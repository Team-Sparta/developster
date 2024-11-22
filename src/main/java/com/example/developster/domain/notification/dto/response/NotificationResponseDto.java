package com.example.developster.domain.notification.dto.response;


import com.example.developster.domain.notification.entity.Notification;
import com.example.developster.domain.notification.enums.NotificationType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record NotificationResponseDto(
        Long id,
        String message,
        Boolean isRead,
        NotificationType type,
        Long userId,
        LocalDateTime createdAt,
        Long referenceId) {

    public static NotificationResponseDto toDto(Notification notification) {
        return NotificationResponseDto.builder()
                .id(notification.getId())
                .message(notification.getMessage())
                .isRead(notification.getIsRead())
                .type(notification.getType())
                .createdAt(notification.getCreatedAt())
                .referenceId(notification.getReference_id())
                .build();
    }
}
