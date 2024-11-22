package com.example.developster.domain.user.main.entity;


import com.example.developster.domain.notification.entity.Notification;
import com.example.developster.domain.post.comment.main.entity.Comment;
import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.user.follow.entity.Follow;
import com.example.developster.domain.user.main.dto.request.UserUpdateRequestDto;
import com.example.developster.global.entity.BaseTimeEntity;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Entity
@Table(name = "users")
public class User extends BaseTimeEntity {

    public enum Role {
        ROLE_ADMIN, ROLE_USER;
    }

    public enum Status {
        BLOCKED, ACTIVE, INACTIVE, WITHDRAWAL;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT UNSIGNED comment '회원 고유 번호'")
    private Long id;

    @Column(name = "email", nullable = false, unique = true, columnDefinition = "varchar(255) comment '이메일'")
    String email;

    @Column(nullable = false)
    String name;

    @Setter
    @Column(nullable = false)
    String password;

    @Column
    String bio;

    @Column
    String profile;

    @Column
    Boolean publicStatus = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(255) DEFAULT 'active'")
    Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "VARCHAR(255) DEFAULT 'role_user'")
    Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE,orphanRemoval = true)
    List<Post> postList = new ArrayList<>();

    @OneToMany(mappedBy = "recipient",cascade = CascadeType.REMOVE,orphanRemoval=true)
    List<Notification> notificationList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE,orphanRemoval = true)
    List<Follow> followList = new ArrayList<>();



    @Builder
    public User(String name, String email, String password, String bio, String profile) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.bio = bio;
        this.profile = profile;

        this.public_status = true;

        this.role = Role.ROLE_ADMIN;
        this.status = Status.ACTIVE;
    }

    public void update(UserUpdateRequestDto req) {
        this.name = req.getName();
        this.bio = req.getBio();
        this.profile = req.getProfile();
        if(req.getPublicStatus()!= null && Objects.equals(req.getPublicStatus(),"public")) {
            this.public_status = true;
        } else if (req.getPublicStatus()!= null && Objects.equals(req.getPublicStatus(),"private")) {
            this.public_status = false;
        }
    }

    public void delete() {
        if (this.deletedAt != null) {
            throw new InvalidParamException(ErrorCode.ALREADY_DELETED_USER);
        }

        this.deletedAt = LocalDateTime.now();
        this.status = Status.WITHDRAWAL;
    }


    @Builder
    public User() {
    }
}