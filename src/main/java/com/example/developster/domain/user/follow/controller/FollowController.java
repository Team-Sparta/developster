package com.example.developster.domain.user.follow.controller;

import com.example.developster.domain.user.follow.dto.AcceptFollowRequestDto;
import com.example.developster.domain.user.follow.dto.UserFollowRequestDto;
import com.example.developster.domain.user.follow.service.FollowService;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.constants.AuthConstants;
import com.example.developster.global.exception.code.SuccessCode;
import com.example.developster.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping
    public ResponseEntity<CommonResponse<Object>> followUser(@RequestBody UserFollowRequestDto userFollowRequestDto,
                                                                        @SessionAttribute (AuthConstants.LOGIN_USER) User user) {

        followService.createFollow(userFollowRequestDto, user);

        return CommonResponse.success(SuccessCode.SUCCESS_INSERT);
    }

    @DeleteMapping("/{followUserId}")
    public ResponseEntity<CommonResponse<Object>> unfollow(
            @PathVariable Long followUserId,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user) {

        followService.unfollow(user, followUserId);

        return CommonResponse.success(SuccessCode.SUCCESS_DELETE);
    }

    @PostMapping("/accept")
    public ResponseEntity<CommonResponse<Object>> acceptFollow(@RequestBody AcceptFollowRequestDto followAcceptRequestDto,
                                                             @SessionAttribute (AuthConstants.LOGIN_USER) User user) {

        followService.acceptFollow(followAcceptRequestDto, user);

        return CommonResponse.success(SuccessCode.SUCCESS_INSERT);
    }

    @DeleteMapping("/accept")
    public ResponseEntity<CommonResponse<Object>> refuseFollow(@RequestBody AcceptFollowRequestDto followAcceptRequestDto,
                                                               @SessionAttribute (AuthConstants.LOGIN_USER) User user) {

        followService.refuseFollow(followAcceptRequestDto, user);

        return CommonResponse.success(SuccessCode.SUCCESS_INSERT);
    }
}
