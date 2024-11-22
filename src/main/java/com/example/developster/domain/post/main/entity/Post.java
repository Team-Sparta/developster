package com.example.developster.domain.post.main.entity;

import com.example.developster.domain.post.main.dto.request.WritePostRequestDto;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.entity.BaseTimeEntity;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@BatchSize(size = 10)
@Table(
        name = "posts"
)
public class Post extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED comment '스케줄 고유 번호'")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, columnDefinition = "BIGINT UNSIGNED comment '회원 고유 번호'")
    private User user;

    @Column(name = "title", nullable = false, columnDefinition = "VARCHAR(30) comment '제목'")
    private String title;

    @Column(name = "content", nullable = false, columnDefinition = "VARCHAR(1200) comment '내용'")
    private String content;

    @Column(name = "is_private", nullable = false, columnDefinition = "BOOLEAN comment '내용'")
    private Boolean isPrivate;

    @Builder
    public Post(Long id, @NotNull User user, @NotNull String title, @NotNull String content, @NotNull Boolean isPrivate) {
        this.id = id;
        this.user = user;
        this.title = title;
        this.content = content;
        this.isPrivate = isPrivate;
    }

    public void update(WritePostRequestDto request) {
        this.title = request.getTitle();
        this.content = request.getContent();
        this.isPrivate = request.getIsPrivate();
    }

    public void delete() {
        if (this.deletedAt != null) {
            throw new InvalidParamException(ErrorCode.ALREADY_DELETED_POST);
        }
        this.deletedAt = LocalDateTime.now();
    }

    public void validatePostWriter(Long userId) {
        if (!userId.equals(this.user.getId())) {
            throw new InvalidParamException(ErrorCode.NOT_POST_WRITER);
        }
    }

    public void reverseValidatePostWriter(Long userId) {
        if (userId.equals(this.user.getId())) {
            throw new InvalidParamException(ErrorCode.CONSTRAINT_VIOLATION);
        }
    }

}
