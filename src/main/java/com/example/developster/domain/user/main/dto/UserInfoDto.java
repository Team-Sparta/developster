package com.example.developster.domain.user.main.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoDto {

    private final Long id;
    private final String name;
    private final String profile;

    @Builder
    public UserInfoDto(String name, String profile, Long id) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }
}
