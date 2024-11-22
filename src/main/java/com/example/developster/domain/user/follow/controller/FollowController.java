package com.example.developster.domain.user.follow.controller;

import com.example.developster.domain.user.follow.dto.AcceptFollowRequestDto;
import com.example.developster.domain.user.follow.dto.FollowListResponseDto;
import com.example.developster.domain.user.follow.dto.UserFollowRequestDto;
import com.example.developster.domain.user.follow.service.FollowService;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.constants.AuthConstants;
import com.example.developster.global.exception.code.SuccessCode;
import com.example.developster.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "팔로우 API",
        description = "다른 사용자 게정을 팔로우하거나 취소한다"
)
@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @Operation(
            summary = "팔로우"
    )
    @PostMapping
    public ResponseEntity<CommonResponse<Object>> followUser(@RequestBody UserFollowRequestDto userFollowRequestDto,
                                                             @Parameter(hidden = true) @SessionAttribute(AuthConstants.LOGIN_USER) User user) {
        followService.createFollow(userFollowRequestDto, user);

        return CommonResponse.success(SuccessCode.SUCCESS_INSERT);
    }

    @Operation(
            summary = "언팔로우"
    )
    @DeleteMapping("/{followUserId}")
    public ResponseEntity<CommonResponse<Object>> unfollow(
            @PathVariable Long followUserId,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user) {

        followService.unfollow(user, followUserId);

        return CommonResponse.success(SuccessCode.SUCCESS_DELETE);
    }

    @Operation(
            summary = "팔로우 승락(팔로우를 요청한 계정이 비공계 일떄)"
    )
    @PostMapping("/accept")
    public ResponseEntity<CommonResponse<Object>> acceptFollow(@RequestBody AcceptFollowRequestDto followAcceptRequestDto,
                                                               @Parameter(hidden = true) @SessionAttribute(AuthConstants.LOGIN_USER) User user) {

        followService.acceptFollow(followAcceptRequestDto, user);

        return CommonResponse.success(SuccessCode.SUCCESS_INSERT);
    }

    @Operation(
            summary = "팔로우 거절(팔로우를 요청한 계정이 비공계 일떄)"
    )
    @DeleteMapping("/accept")
    public ResponseEntity<CommonResponse<Object>> refuseFollow(@RequestBody AcceptFollowRequestDto followAcceptRequestDto,
                                                               @Parameter(hidden = true) @SessionAttribute(AuthConstants.LOGIN_USER) User user) {

        followService.refuseFollow(followAcceptRequestDto, user);

        return CommonResponse.success(SuccessCode.SUCCESS_INSERT);
    }

    @Operation(
            summary = "내 팔로우 리스트 조회"
    )
    @GetMapping("/list")
    public ResponseEntity<CommonResponse<FollowListResponseDto>> followList(@SessionAttribute(AuthConstants.LOGIN_USER) User user) {
        return CommonResponse.success(SuccessCode.SUCCESS, followService.followList(user));
    }
}
