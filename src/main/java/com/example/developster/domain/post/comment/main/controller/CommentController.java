package com.example.developster.domain.post.comment.main.controller;

import com.example.developster.domain.post.comment.main.dto.CommentUpdateRequestDto;
import com.example.developster.domain.post.comment.main.dto.CommentUpdateResponseDto;
import com.example.developster.domain.post.comment.main.dto.summary.CommentsSummaries;
import com.example.developster.domain.post.comment.main.dto.summary.CommentSummariesDetail;
import com.example.developster.domain.post.comment.main.dto.summary.RepliesSummaries;
import com.example.developster.domain.post.comment.main.dto.summary.RepliesSummariesDetail;
import com.example.developster.domain.post.comment.main.dto.CommentCreateRequestDto;
import com.example.developster.domain.post.comment.main.dto.CommentCreateResponseDto;
import com.example.developster.domain.post.comment.main.service.CommentService;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.constants.AuthConstants;
import com.example.developster.global.exception.code.SuccessCode;
import com.example.developster.global.response.CommonResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController{

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<CommonResponse<CommentCreateResponseDto>> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateRequestDto dto,
            @SessionAttribute(AuthConstants.LOGIN_USER) User loginUser
    ){

        CommentCreateResponseDto resDto =  commentService.createComment(dto,postId, loginUser);
        return CommonResponse.success(SuccessCode.SUCCESS_INSERT,resDto);
    }

    //댓글 조회
    @GetMapping
    public ResponseEntity<CommonResponse<CommentsSummaries>> readComments(
            @PathVariable Long postId,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size,
            @SessionAttribute(AuthConstants.LOGIN_USER) User loginUser

    ){
        CommentSummariesDetail resDetail = commentService.readComments(loginUser,postId,lastId, size);
        return CommonResponse.success(SuccessCode.SUCCESS, new CommentsSummaries(resDetail));
    }

    //답글 조회
    @GetMapping("/{commentId}")
    public ResponseEntity<CommonResponse<RepliesSummaries>> readReplies(
            @PathVariable Long commentId,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size,
            @SessionAttribute(AuthConstants.LOGIN_USER) User loginUser
    ){
        RepliesSummariesDetail resDetail =  commentService.readReplies(loginUser,commentId,lastId,size);
        return CommonResponse.success(SuccessCode.SUCCESS, new RepliesSummaries(resDetail));
    }

    //댓글,답글 수정
    @PutMapping("/{commentId}")
    public ResponseEntity<CommonResponse<CommentUpdateResponseDto>> updateComment(
            @PathVariable Long commentId,
            @SessionAttribute(AuthConstants.LOGIN_USER) User user,
            @Valid @RequestBody CommentUpdateRequestDto dto
    ){
        CommentUpdateResponseDto resDto = commentService.updateComment(commentId,user,dto);
        return CommonResponse.success(SuccessCode.SUCCESS_UPDATE, resDto);
    }

    //댓글 삭제
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponse<Void>> deleteComment(
            @PathVariable Long commentId,
            @SessionAttribute(AuthConstants.LOGIN_USER) User loginUser
    ){
        commentService.deleteComment(commentId, loginUser);
        return CommonResponse.success(SuccessCode.SUCCESS_DELETE);
    }
}
