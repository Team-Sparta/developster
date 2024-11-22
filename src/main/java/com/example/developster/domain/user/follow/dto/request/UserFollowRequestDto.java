package com.example.developster.domain.user.follow.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class UserFollowRequestDto {
    @NotNull
    private final Long id;

    @Builder
    public UserFollowRequestDto(Long id) {
        this.id = id;
    }
}
