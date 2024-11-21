package com.example.developster.domain.notification.dto.request;

import com.example.developster.domain.notification.enums.NotificationType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

public record CreateNotificationRequest(
        @NotNull
        NotificationType notificationType,
        @NotNull
        @Schema(description = "알림 내용")
        String content,
        @NotNull
        @Schema(description = "연관 id")
        Long referenceId
) {
}