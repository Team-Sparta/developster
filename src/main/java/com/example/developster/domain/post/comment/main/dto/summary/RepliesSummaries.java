package com.example.developster.domain.post.comment.main.dto.summary;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RepliesSummaries {
    @JsonProperty("repliesSummaries")
    private RepliesSummariesDetail details;

    public RepliesSummaries(RepliesSummariesDetail detail){
        this.details = detail;
    }
}
