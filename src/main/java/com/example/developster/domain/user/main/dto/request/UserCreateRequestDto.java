package com.example.developster.domain.user.main.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserCreateRequestDto {
    @NotNull
    private final String name;
    @NotNull
    private final String email;
    @NotNull
    private final String password;
    private final String bio;
    private final String profile;



    @Builder
    public UserCreateRequestDto(String name, String email, String password, String bio, String profile) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.profile = profile;
    }

}
