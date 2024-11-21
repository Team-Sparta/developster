package com.example.developster.domain.post.like.entity;

import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.entity.BaseCreatedTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
        name = "post_likes",
        indexes = {
                @Index(name = "idx_post_id", columnList = "post_id"),
                @Index(name = "idx_user_id", columnList = "user_id"),
        }
)
public class PostLike extends BaseCreatedTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED comment '게시물 좋아요 고유 번호'")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "BIGINT UNSIGNED comment '회원 고유 번호'")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id", nullable = false, columnDefinition = "BIGINT UNSIGNED comment '게시물 고유 번호'")
    private Post post;

    @Setter
    @Column(name = "is_liked", nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE comment '게시물 좋아요 여부'")
    private Boolean isLiked = true;

    public static PostLike create(@NotNull User user, @NotNull Post post, @NotNull Boolean isLiked) {
        return PostLike.builder()
                .user(user)
                .post(post)
                .isLiked(isLiked)
                .build();
    }
}
