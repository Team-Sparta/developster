package com.example.developster.domain.user.main.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginRequestDto {
    private final String email;
    private final String password;


    @Builder
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
