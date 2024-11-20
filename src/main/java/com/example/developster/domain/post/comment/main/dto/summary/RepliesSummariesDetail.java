package com.example.developster.domain.post.comment.main.dto.summary;

import com.example.developster.domain.post.comment.main.dto.CommentReadResDto;

import java.util.List;

public class RepliesSummariesDetail {
    private final boolean first;
    private final boolean last;
    private final int size;
    private final List<CommentReadResDto> resDtoList;

    public RepliesSummariesDetail(boolean first, boolean last, int size, List<CommentReadResDto> resDtoList) {
        this.first = first;
        this.last = last;
        this.size = size;
        this.resDtoList = resDtoList;
    }
}
