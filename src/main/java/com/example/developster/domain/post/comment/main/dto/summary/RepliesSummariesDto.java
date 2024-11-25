package com.example.developster.domain.post.comment.main.dto.summary;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RepliesSummariesDto {
    @JsonProperty("repliesSummaries")
    private RepliesSummariesDetailDto details;

    public RepliesSummariesDto(RepliesSummariesDetailDto detail){
        this.details = detail;
    }
}
