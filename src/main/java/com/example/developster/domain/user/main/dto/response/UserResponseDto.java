package com.example.developster.domain.user.main.dto.response;

import com.example.developster.domain.user.main.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private final Long id;
    private final String name;
    private final String email;
    private final String bio;
    private final String profile;

    @Builder
    public UserResponseDto(Long id, String name, String email, String bio, String profile) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.profile = profile;
    }

    public static UserResponseDto toDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .bio(user.getBio())
                .profile(user.getProfile()).build();
    }
}
