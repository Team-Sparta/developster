package com.example.developster.domain.post.comment.main.dto.summary;

import com.example.developster.domain.post.comment.main.dto.CommentDetailInfo;
import com.example.developster.domain.post.comment.main.dto.CommentReadResponseDto;
import lombok.Getter;
import org.springframework.data.domain.Slice;

import java.util.List;

public record RepliesSummariesDetail(
    Slice<CommentDetailInfo> allReplies
){}
