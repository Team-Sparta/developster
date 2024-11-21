package com.example.developster.domain.post.like.controller;

import com.example.developster.domain.post.like.service.PostLikeService;
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
        name = "게시물 좋아요 API",
        description = "게시물 좋아요 관련 API"
)
@RequiredArgsConstructor
@RestController
@RequestMapping("/posts/{postId}/likes")
public class PostLikeController {

    private final PostLikeService postLikeService;

    @Operation(
            summary = "게시물 좋아요",
            description = "게시물에 좋아요를 등록한다."
    )
    @PostMapping
    public ResponseEntity<CommonResponse<Void>> likePost(
            @PathVariable Long postId,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user) {

        postLikeService.likePost(user, postId);

        return CommonResponse.success(SuccessCode.SUCCESS_INSERT);
    }

    @Operation(
            summary = "게시물 좋아요를 취소",
            description = "게시물에 좋아요를 취소한다."
    )
    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> unlikePost(
            @PathVariable Long postId,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user) {

        postLikeService.unlikePost(user, postId);

        return CommonResponse.success(SuccessCode.SUCCESS_DELETE);
    }
}
