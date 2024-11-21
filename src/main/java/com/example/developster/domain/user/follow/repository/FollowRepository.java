package com.example.developster.domain.user.follow.repository;

import com.example.developster.domain.user.follow.entity.Follow;
import com.example.developster.domain.user.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository <Follow, Long> {
    Optional<Follow> findByUserAndFollowedUser(User user, User followedUser);
}
