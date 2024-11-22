package com.example.developster.domain.user.follow.dto;

import java.util.List;

public record FollowListResponseDto (
        List<FollowResponseSummaryDto> follows
) {


}
