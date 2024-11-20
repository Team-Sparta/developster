package com.example.developster.domain.post.comment.main;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CommentSummaries {
    @JsonProperty("commentsSummaries")
    private CommentSummariesDetail details;

    public CommentSummaries(CommentSummariesDetail detail){
        this.details = detail;
    }
}
