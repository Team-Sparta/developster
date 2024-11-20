package com.example.developster.domain.user.main.dto;

import com.example.developster.domain.user.main.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserResponsDto {
    private final String name;
    private final String email;
    private final String bio;
    private final String prifile;

    @Builder
    public UserResponsDto(String name, String email, String bio, String profile) {
        this.name = name;
        this.email = email;
        this.bio = bio;
        this.prifile = profile;
    }

    public static UserResponsDto toDto(User user) {
        return UserResponsDto.builder()
                .name(user.getName())
                .email(user.getEmail())
                .bio(user.getBio())
                .profile(user.getProfile()).build();
    }
}
