package com.example.developster.domain.user.main.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserIdResponseDto {
    private final Long id;

    @Builder
    public UserIdResponseDto(Long id) {
        this.id = id;
    }
}
