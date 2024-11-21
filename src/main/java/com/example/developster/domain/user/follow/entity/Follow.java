package com.example.developster.domain.user.follow.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Entity
public class Follow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED")
    private Long id;

    @Column(name = "user_id", columnDefinition = "BIGINT UNSIGNED")
    private Long userId;

    @Column(name = "follow_user_id", columnDefinition = "BIGINT UNSIGNED")
    private Long followUserId;

}
