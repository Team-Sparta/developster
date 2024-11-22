package com.example.developster.domain.user.follow.dto.response;

import com.example.developster.domain.user.follow.dto.FollowResponseSummaryDto;

import java.util.List;

public record FollowListResponseDto (
        List<FollowResponseSummaryDto> follows
) {


}
