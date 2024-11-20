package com.example.developster.domain.user.main.dto;

import lombok.Builder;

public class UserIdResponseDto {
    private final Long id;

    @Builder
    public UserIdResponseDto(Long id) {
        this.id = id;
    }
}
