package com.example.developster.domain.user.main.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    @NotNull
    private final String email;
    @NotNull
    private final String password;


    @Builder
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
