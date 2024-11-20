package com.example.developster.domain.notification.dto;

import lombok.Getter;

@Getter
public class NotificationRequestDto {

    private final Boolean isRead;

    public NotificationRequestDto(Boolean isRead) {
        this.isRead = isRead;
    }
}
