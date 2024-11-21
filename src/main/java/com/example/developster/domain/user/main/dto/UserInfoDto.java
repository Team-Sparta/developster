package com.example.developster.domain.user.main.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(force = true)
@Getter
public class UserInfoDto {

    private final Long id;
    private final String name;
    private final String profile;

    @Builder
    public UserInfoDto(Long id,String name, String profile) {
        this.id = id;
        this.name = name;
        this.profile = profile;
    }
}
