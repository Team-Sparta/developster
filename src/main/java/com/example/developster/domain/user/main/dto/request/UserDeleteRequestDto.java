package com.example.developster.domain.user.main.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDeleteRequestDto {
    @NotNull
    private final String password;

    @Builder
    public UserDeleteRequestDto(String password) {
        this.password = password;
    }
}
