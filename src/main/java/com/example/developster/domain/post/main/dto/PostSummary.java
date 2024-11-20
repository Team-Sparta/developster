package com.example.developster.domain.post.main.dto;

import com.example.developster.domain.post.main.entity.Post;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PostSummary {
    private Long postId;
    private String title;
    private String content;
    private List<String> imageUrlList;
    private String videoUrl;
    private Long likeCount;
    private Long CommentCount;
    private LocalDateTime createdAt;

    public static PostSummary of(Post post) {
        return PostSummary.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
