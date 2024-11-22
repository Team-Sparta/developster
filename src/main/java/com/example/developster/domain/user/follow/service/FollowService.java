package com.example.developster.domain.user.follow.service;

import com.example.developster.domain.notification.dto.NotificationResponseDto;
import com.example.developster.domain.notification.enums.NotificationType;
import com.example.developster.domain.notification.service.NotificationService;
import com.example.developster.domain.post.main.entity.Post;
import com.example.developster.domain.user.follow.dto.AcceptFollowRequestDto;
import com.example.developster.domain.user.follow.dto.FollowListResponseDto;
import com.example.developster.domain.user.follow.dto.FollowResponseSummaryDto;
import com.example.developster.domain.user.follow.dto.UserFollowRequestDto;
import com.example.developster.domain.user.follow.entity.Follow;
import com.example.developster.domain.user.follow.repository.FollowRepository;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.domain.user.main.repository.UserRepository;
import com.example.developster.global.exception.BaseException;
import com.example.developster.global.exception.code.ErrorCode;
import org.springframework.stereotype.Service;

import java.net.ResponseCache;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static io.micrometer.common.util.StringUtils.truncate;

@Service
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final NotificationService notificationService;


    public FollowService(UserRepository userRepository, FollowRepository followRepository, NotificationService notificationService) {
        this.userRepository = userRepository;
        this.followRepository = followRepository;
        this.notificationService = notificationService;
    }

    public void createFollow(UserFollowRequestDto userFollowRequestDto, User user) {

        User followedUser = userRepository.findByIdOrElseThrow(userFollowRequestDto.getId());

        Follow follow;
        if (followedUser.getPublic_status()) {
            follow = Follow.builder()
                    .user(user)
                    .followedUser(followedUser)
                    .status(Follow.Status.ACCEPT).build();
        } else {
            follow = Follow.builder()
                    .user(user)
                    .followedUser(followedUser)
                    .status(Follow.Status.REQUEST).build();
        }

        Follow savedfollow = followRepository.save(follow);

        sendFollowNotification(user, followedUser, savedfollow.getId());

    }

    public void unfollow(User user, Long followUserId) {
        User followedUser = userRepository.findByIdOrElseThrow(followUserId);
        Optional<Follow> follow = followRepository.findByUserAndFollowedUser(user, followedUser);

        if (follow.isEmpty()) {
            throw new BaseException(ErrorCode.NOT_FOUND_FOLLOW);
        }

        followRepository.delete(follow.get());
    }


    public void acceptFollow(AcceptFollowRequestDto followAcceptRequestDto, User user) {
        Follow follow = followRepository.findByIdOrElseThrow(followAcceptRequestDto.getId());


        follow.accept(follow);
    }

    public void refuseFollow(AcceptFollowRequestDto followAcceptRequestDto, User user) {

        Follow follow = followRepository.findByIdOrElseThrow(followAcceptRequestDto.getId());

        followRepository.delete(follow);
    }

    public FollowListResponseDto followList(User user) {

        List<Follow> follows = followRepository.findAllByStatusAndUser_Id(Follow.Status.ACCEPT, user.getId());

        List<FollowResponseSummaryDto> list = follows.stream().map(FollowResponseSummaryDto::toDto).toList();

        return new FollowListResponseDto(list);
    }

    private void sendFollowNotification(User user, User followedUser, Long followId) {
        String message;
        if (followedUser.getPublic_status()) {
            message = user.getName() + "님이 " + followedUser.getName() + "님을 팔로우합니다.";
        } else {
            message = user.getName() + "님이 " + followedUser.getName() + "님에게 팔로우를 요청했습니다.";
        }

        notificationService.sendNotification(
                followedUser,
                user,
                message,
                followId,
                NotificationType.COMMENT
        );
    }
}
