package com.example.developster.domain.post.comment.main.dto;

import com.example.developster.domain.post.comment.main.entity.Comment;
import com.example.developster.domain.user.main.dto.UserInfoDto;
import lombok.Getter;

@Getter
public class CommentReadResponseDto {
    private final Long postId;
    private final Long commentId;
    private final String contents;
    private final UserInfoDto userInfo;
    public CommentReadResponseDto(Long postId, Long commentId, String contents, UserInfoDto userInfo) {
        this.postId = postId;
        this.commentId = commentId;
        this.contents = contents;
        this.userInfo =userInfo;
    }
    public CommentReadResponseDto(Comment comment){
        this.postId = comment.getPost().getId();
        this.commentId = comment.getId();
        this.contents = comment.getContents();

        this.userInfo = UserInfoDto.builder()
                .id(comment.getUser().getId())
                .name(comment.getUser().getName())
                .profile(comment.getUser().getProfile())
                .build();
    }
}
