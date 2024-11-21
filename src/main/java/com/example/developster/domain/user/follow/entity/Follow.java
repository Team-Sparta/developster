package com.example.developster.domain.user.follow.entity;

import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;


@Getter
@NoArgsConstructor
@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @OneToOne
    @NotNull
    @JoinColumn(name = "user_id", columnDefinition = "BIGINT UNSIGNED")
    private User user;

    @OneToOne
    @NotNull
    @JoinColumn(name = "follow_user_id", columnDefinition = "BIGINT UNSIGNED")
    private User followedUser;

    @CreatedDate
    @Column(name = "followed_at", updatable = false, columnDefinition = "datetime")
    private LocalDateTime followedAt;

    @Builder
    public Follow(User user, User followedUser) {
        this.user = user;
        this.followedUser = followedUser;
    }
}
