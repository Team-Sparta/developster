package com.example.developster.domain.post.comment.main.dto.summary;

import com.example.developster.domain.post.comment.main.dto.CommentDetailInfo;
import org.springframework.data.domain.Slice;

public record RepliesSummariesDetail(
    Slice<CommentDetailInfo> allReplies
){}
