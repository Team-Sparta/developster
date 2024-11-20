package com.example.developster.domain.post.media.entity;

import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.global.entity.BaseTimeEntity;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@Table(
        name = "medias"
)
public class Media extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED comment '파일 고유 번호'")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id", columnDefinition = "BIGINT UNSIGNED comment '일상생활 고유 번호'")
    private Post post;

    @Column(name = "url", columnDefinition = "VARCHAR(255) comment '파일 경로'")
    private String url;


    public static Media create(Post post, String url) {
        return Media.builder()
                .post(post)
                .url(url)
                .build();
    }

    public void delete() {
        if (this.deletedAt != null) {
            throw new InvalidParamException(ErrorCode.ALREADY_DELETED_POST);
        }

        this.deletedAt = LocalDateTime.now();
    }

}