package com.example.developster.domain.user.main.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class UserDeleteResponseDto {

    private final Long id;

    @Builder
    public UserDeleteResponseDto(Long id) {
        this.id = id;
    }
}
