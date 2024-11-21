package com.example.developster.domain.post.like.controller;

import com.example.developster.domain.post.like.service.PostLikeService;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.constants.AuthConstants;
import com.example.developster.global.exception.code.SuccessCode;
import com.example.developster.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts/{postId}/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<CommonResponse<Object>> likePost(
            @PathVariable Long postId,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user) {

        postLikeService.likePost(user, postId);

        return CommonResponse.success(SuccessCode.SUCCESS_INSERT);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<Object>> unlikePost(
            @PathVariable Long postId,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user) {

        postLikeService.unlikePost(user, postId);

        return CommonResponse.success(SuccessCode.SUCCESS_DELETE);
    }
}
