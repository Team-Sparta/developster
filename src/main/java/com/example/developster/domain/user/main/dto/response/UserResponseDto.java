package com.example.developster.domain.user.main.dto.response;

import com.example.developster.domain.user.main.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final String name;
    private final String email;
    private final String bio;
    private final String prifile;

    @Builder
    public UserResponseDto(String name, String email, String bio, String profile) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.prifile = profile;
    }

    public static UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .bio(user.getBio())
                .profile(user.getProfile()).build();
    }
}
