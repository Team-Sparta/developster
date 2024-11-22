package com.example.developster.domain.user.follow.repository;

import com.example.developster.domain.user.follow.entity.Follow;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FollowRepository extends JpaRepository <Follow, Long> {
    Optional<Follow> findByUserAndFollowedUser(User user, User followedUser);

    default Follow findByIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new InvalidParamException(ErrorCode.NOT_FOUND_FOLLOW));
    }
}
