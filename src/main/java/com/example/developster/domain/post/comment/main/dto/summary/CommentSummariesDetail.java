package com.example.developster.domain.post.comment.main;

import com.example.developster.domain.post.comment.main.dto.CommentReadResDto;
import lombok.Getter;

import java.util.List;

@Getter
public class CommentSummariesDetail {
    private final boolean first;
    private final boolean last;
    private final int size;
    private final List<CommentReadResDto> resDtoList;

    public CommentSummariesDetail(boolean first, boolean last, int size, List<CommentReadResDto> resDtoList) {
        this.first = first;
        this.last = last;
        this.size = size;
        this.resDtoList = resDtoList;
    }
}
