package com.example.developster.domain.post.comment.main.dto;

import com.example.developster.domain.post.comment.main.entity.Comment;
import lombok.Getter;

@Getter
public class CommentReadResDto {
    private final Long postId;
    private final Long commentId;
    private final String contents;

    public CommentReadResDto(Long postId, Long commentId, String contents) {
        this.postId = postId;
        this.commentId = commentId;
        this.contents = contents;
    }
    public CommentReadResDto(Comment comment){
        this.postId = comment.getPost().getId();
        this.commentId = comment.getId();
        this.contents = comment.getContents();
    }
}
