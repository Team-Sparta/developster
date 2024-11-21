package com.example.developster.domain.user.main.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Getter
public class UserDeleteResponseDto {

    private final Long id;
    private final LocalDateTime status;

    @Builder
    public UserDeleteResponseDto(Long id, LocalDateTime status) {
        this.id = id;
        this.status = status;
    }
}
