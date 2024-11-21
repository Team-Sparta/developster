package com.example.developster.domain.post.main.dto;

import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.post.media.dto.MediaInfo;
import com.example.developster.domain.user.main.dto.UserInfoDto;
import com.example.developster.domain.user.main.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

//@Getter
//@Builder
//@AllArgsConstructor
//public class PostSummary {
//    private Long postId;
//    private String title;
//    private String content;
//    private MediaInfo mediaInfo;
//    private Long likeCount;
//    private Long commentCount;
//    private Boolean isLiked;
//    private Boolean isPrivate;
//    private LocalDateTime createdAt;
//    private UserInfoDto author;
//
//    public static PostSummary of(PostDetailInfo post, MediaInfo mediaInfo) {
//        return PostSummary.builder()
//                .postId(post.postId())
//                .title(post.title())
//                .content(post.content())
//                .createdAt(post.createdAt())
//                .author(post.userInfo())
//                .likeCount(post.likeCount())
//                .commentCount(post.commentCount())
//                .mediaInfo(mediaInfo)
//                .build();
//    }
//}


public record PostSummary(
        Long postId,
        String title,
        String content,
        Long likeCount,
        Long commentCount,
        Boolean isLiked,
        Boolean isPrivate,
        LocalDateTime createdAt,
        MediaInfo mediaInfo,
        UserInfoDto author
) {

}