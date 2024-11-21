package com.example.developster.domain.user.follow.service;

import com.example.developster.domain.user.follow.dto.UserFollowRequestDto;
import com.example.developster.domain.user.follow.entity.Follow;
import com.example.developster.domain.user.follow.repository.FollowRepository;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.domain.user.main.repository.UserRepository;
import com.example.developster.global.exception.BaseException;
import com.example.developster.global.exception.code.ErrorCode;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;

    public FollowService(UserRepository userRepository, FollowRepository followRepository) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
    }

    public void createFollow(UserFollowRequestDto userFollowRequestDto, User user) {

        User followedUser = userRepository.findByIdOrElseThrow(userFollowRequestDto.getId());

        Follow follow = Follow.builder().user(user).followedUser(followedUser).build();
        followRepository.save(follow);

    }

    public void unfollow(User user, Long followUserId) {
        User followedUser = userRepository.findByIdOrElseThrow(followUserId);
        Optional<Follow> follow = followRepository.findByUserAndFollowedUser(user, followedUser);

        if (follow.isEmpty()) {
            throw new BaseException(ErrorCode.NOT_FOUND_FOLLOW);
        }

        followRepository.delete(follow.get());
    }
}
