package com.example.developster.domain.post.comment.like.controller;

import com.example.developster.domain.post.comment.like.service.CommentLikeService;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.constants.AuthConstants;
import com.example.developster.global.exception.code.SuccessCode;
import com.example.developster.global.response.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments/{commentId}/like")
public class CommentLikeController {

    private final CommentLikeService likeService;

    @PostMapping
    public ResponseEntity<CommonResponse<Void>> likeComment(
            @PathVariable Long commentId,
            @SessionAttribute(AuthConstants.LOGIN_USER) User user
    ){
        likeService.likeComment(commentId, user);
        return CommonResponse.success(SuccessCode.SUCCESS_INSERT);
    }

    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> unlikeComment(
            @PathVariable Long commentId,
            @SessionAttribute(AuthConstants.LOGIN_USER) User user
    ){
        likeService.unlikeComment(commentId, user);
        return CommonResponse.success(SuccessCode.SUCCESS);
    }
}
