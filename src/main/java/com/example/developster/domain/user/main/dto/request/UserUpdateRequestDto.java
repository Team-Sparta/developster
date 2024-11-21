package com.example.developster.domain.user.main.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserUpdateRequestDto {
    private final String name;
    private final String currentPassword;
    private final String newPassword;
    private final String bio;
    private final String profile;



    @Builder
    public UserUpdateRequestDto(String name, String currentPassword, String newPassword, String bio, String profile) {
        this.name = name;
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.bio = bio;
        this.profile = profile;
    }
}
