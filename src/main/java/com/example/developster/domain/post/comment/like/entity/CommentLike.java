package com.example.developster.domain.post.comment.like.entity;

import com.example.developster.domain.post.comment.main.entity.Comment;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "comment_likes"
)
public class CommentLike extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( columnDefinition = "BIGINT UNSIGNED comment '댓글 고유 번호'")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "BIGINT UNSIGNED comment '댓글 고유 번호'")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "comment_id", nullable = false, columnDefinition = "BIGINT UNSIGNED comment '부모 댓글 번호'")
    private Comment comment;

    @Setter
    @Column(name = "is_like", nullable = false)
    Boolean isLike = true;

    @Builder
    public CommentLike(User user,Comment comment){
        this.user = user;
        this.comment = comment;
        this.isLike = true;
    }
}
