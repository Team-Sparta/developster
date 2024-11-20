package com.example.developster.domain.user.main.entity;


import com.example.developster.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

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
    private Long id;

    @Column(nullable = false, unique = true)
    String email;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    String password;

    @Column
    String bio;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "VARCHAR(255) DEFAULT 'active'")
    Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "VARCHAR(255) DEFAULT 'role_admin'")
    Role role;

    @Builder
    public User(String name, String email, String password, String bio){
        this.name = name;
        this.email = email;
        this.password = password;
        this.bio = bio;

        this.role = Role.ROLE_ADMIN;
        this.status = Status.ACTIVE;
    }


    @Builder
    public User() {
    }


}