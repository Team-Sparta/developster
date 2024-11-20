package com.example.developster.domain.post.comment.main.dto.summary;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentsSummaries {
    @JsonProperty("commentsSummaries")
    private CommentSummariesDetail details;

    public CommentsSummaries(CommentSummariesDetail detail){
        this.details = detail;
    }
}
