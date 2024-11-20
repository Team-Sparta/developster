package com.example.developster.domain.user.main.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoDto {

    private final String name;
    private final String profile;
    private final Long id;

    @Builder
    public UserInfoDto(String name, String profile, Long id) {
        this.name = name;
        this.profile = profile;
        this.id = id;
    }

}
