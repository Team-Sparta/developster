package com.example.developster.domain.post.comment.main.entity;

import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.entity.BaseTimeEntity;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "comments"
)
public class Comment extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( columnDefinition = "BIGINT UNSIGNED comment '댓글 고유 번호'")
    private Long id;

    @Setter
    @NotBlank
    @Column(name = "contents", columnDefinition = "VARCHAR(200) comment '댓글 내용'")
    private String contents;

    @ManyToOne
    @JoinColumn(name = "post_id", columnDefinition = "BIGINT UNSIGNED comment '게시물 고유 번호'")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "parent_id", columnDefinition = "BIGINT UNSIGNED comment '부모 댓글 번호'")
    private Comment parentComment;

    @ManyToOne
    @JoinColumn(name = "user_id", columnDefinition = "BIGINT UNSIGNED comment '유저 고유 번호'")
    private User user;

    @OneToMany(mappedBy = "parentComment", orphanRemoval = true)
    List<Comment> commentList = new ArrayList<>();

    @Builder
    public Comment(String contents, User user, Post post, Comment comment){
        this.contents =contents;
        this.user = user;
        this.post = post;
        this.parentComment = comment;
    }

    public void delete() {
        if (this.deletedAt != null) {
            throw new InvalidParamException(ErrorCode.ALREADY_DELETED_POST);
        }
        this.deletedAt = LocalDateTime.now();
    }
}
