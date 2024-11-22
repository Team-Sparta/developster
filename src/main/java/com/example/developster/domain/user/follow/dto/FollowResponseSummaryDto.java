package com.example.developster.domain.user.follow.dto;

import com.example.developster.domain.user.follow.entity.Follow;
import com.example.developster.domain.user.main.dto.UserInfoDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class FollowResponseSummaryDto {
    private Long id;
    private UserInfoDto userInfo;
    private LocalDateTime followedAt;
    private Follow.Status status;

    public static FollowResponseSummaryDto toDto(Follow follow) {
        return FollowResponseSummaryDto.builder()
                .id(follow.getId())
                .userInfo(new UserInfoDto(follow.getUser().getId(), follow.getUser().getName(), follow.getUser().getProfile()))
                .followedAt(follow.getFollowedAt())
                .build();
    }

}
