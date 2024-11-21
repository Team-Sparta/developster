package com.example.developster.domain.post.comment.main.dto.summary;

import com.example.developster.domain.post.comment.main.dto.CommentReadResponseDto;
import lombok.Getter;

import java.util.List;

@Getter
public class RepliesSummariesDetail {
    private final boolean first;
    private final boolean last;
    private final int size;
    private final List<CommentReadResponseDto> resDtoList;

    public RepliesSummariesDetail(boolean first, boolean last, int size, List<CommentReadResponseDto> resDtoList) {
        this.first = first;
        this.last = last;
        this.size = size;
        this.resDtoList = resDtoList;
    }
}
