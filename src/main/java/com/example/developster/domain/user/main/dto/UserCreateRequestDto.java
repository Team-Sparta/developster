package com.example.developster.domain.user.main.dto;

import com.example.developster.domain.user.main.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserRequestDto {
    private final String name;
    private final String email;
    private final String password;
    private final String bio;
    private final String profile;



    @Builder
    public UserRequestDto(String name, String email, String password, String bio, String profile) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.profile = profile;
    }

}
