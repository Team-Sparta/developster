package com.example.developster.domain.post.comment.main.dto.summary;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentsSummariesDto {
    @JsonProperty("commentsSummaries")
    private CommentSummariesDetailDto details;

    public CommentsSummariesDto(CommentSummariesDetailDto detail) {
        this.details = detail;
    }
}