package com.example.developster.domain.post.main.dto;

import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.user.main.dto.UserInfoDto;
import com.example.developster.domain.user.main.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostSummary {
    private Long postId;
    private String title;
    private String content;
    private List<String> imageUrlList;
    private String videoUrl;
    private Long likeCount;
    private Long CommentCount;
    private Boolean isLiked;
    private Boolean isPrivate;
    private LocalDateTime createdAt;
    private UserInfoDto author;

    public static PostSummary of(User user, Post post) {
        return PostSummary.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .author(new UserInfoDto(user.getName(), user.getProfile(), user.getId()))
                .build();
    }
}
