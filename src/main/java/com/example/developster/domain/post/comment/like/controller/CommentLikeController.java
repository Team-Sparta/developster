package com.example.developster.domain.post.comment.like.controller;

import com.example.developster.domain.post.comment.like.service.CommentLikeService;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.constants.AuthConstants;
import com.example.developster.global.exception.code.SuccessCode;
import com.example.developster.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@Tag(
        name = "댓글 좋아요 API",
        description = "댓글 좋아요 관련 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments/{commentId}/like")
public class CommentLikeController {

    private final CommentLikeService likeService;

    @Operation(
            summary = "댓글 좋아요 생성",
            description = """
                    댓글에 좋아요를 생성합니다.
                    자신의 댓글에 좋아요를 생성할 수 없고,
                    한번 좋아요 누른 댓글은 좋아요를 누를 수 없습니다.
                    """
    )
    @PostMapping
    public ResponseEntity<CommonResponse<Void>> likeComment(
            @PathVariable Long commentId,
            @SessionAttribute(AuthConstants.LOGIN_USER) User user
    ){
        likeService.likeComment(commentId, user);
        return CommonResponse.success(SuccessCode.SUCCESS_INSERT);
    }

    @Operation(
            summary = "댓글 좋아요 취소",
            description = """
                    댓글 좋아요를 취소합니다.
                    """
    )
    @DeleteMapping
    public ResponseEntity<CommonResponse<Void>> unlikeComment(
            @PathVariable Long commentId,
            @SessionAttribute(AuthConstants.LOGIN_USER) User user
    ){
        likeService.unlikeComment(commentId, user);
        return CommonResponse.success(SuccessCode.SUCCESS);
    }
}
