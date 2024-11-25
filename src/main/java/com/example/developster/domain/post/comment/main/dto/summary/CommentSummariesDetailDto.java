package com.example.developster.domain.post.comment.main.dto.summary;

import com.example.developster.domain.post.comment.main.dto.CommentDetailInfoDto;
import org.springframework.data.domain.Slice;

public record CommentSummariesDetailDto(
    Slice<CommentDetailInfoDto> allComments){}

