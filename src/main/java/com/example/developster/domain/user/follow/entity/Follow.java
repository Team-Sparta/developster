package com.example.developster.domain.user.follow.entity;

import com.example.developster.domain.user.main.entity.User;
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
@Table(name = "follows")
public class Follow {

    public enum Status {
        REQUEST, ACCEPT;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name = "user_id", columnDefinition = "BIGINT UNSIGNED")
    private User user;

    @OneToOne
    @NotNull
    @JoinColumn(name = "follow_user_id", columnDefinition = "BIGINT UNSIGNED")
    private User followedUser;

    @CreatedDate
    @Column(name = "followed_at", updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private LocalDateTime followedAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(255) DEFAULT 'ACCEPT'")
    Status status;

    @Builder
    public Follow(User user, User followedUser, Status status) {
        this.user = user;
        this.followedUser = followedUser;
        this.followedAt = LocalDateTime.now();
        this.status = status;
    }

    public void accept(Follow follow) {
        this.status = Status.ACCEPT;
    }

}
