package com.example.developster.domain.user.follow.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(force = true)
public class AcceptFollowRequestDto {

    @NotNull
    private final Long id;

    @Builder
    public AcceptFollowRequestDto(Long id) {
        this.id = id;
    }
}
