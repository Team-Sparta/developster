package com.example.developster.domain.post.comment.like.entity;

import com.example.developster.domain.post.comment.main.entity.Comment;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "comment_likes"
)
public class CommentLike extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    @Builder
    public CommentLike(User user, Comment comment){
        this.user = user;
        this.comment = comment;
    }
}
