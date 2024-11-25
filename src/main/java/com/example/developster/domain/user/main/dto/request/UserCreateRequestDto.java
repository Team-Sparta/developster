package com.example.developster.domain.user.main.dto.request;

import jakarta.validation.constraints.NotBlank;import jakarta.validation.constraints.NotEmpty;
import lombok.Builder;

public record UserCreateRequestDto(
        @NotBlank String name,
        @NotBlank String email,
        @NotBlank String password,
        String bio,
        String profile) {

    @Builder
    public UserCreateRequestDto(String name, String email, String password, String bio, String profile) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.profile = profile;
    }
}
