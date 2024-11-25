package com.example.developster.domain.post.comment.main.controller;

import com.example.developster.domain.post.comment.main.dto.response.CommentUpdateRequestDto;
import com.example.developster.domain.post.comment.main.dto.response.CommentUpdateResponseDto;
import com.example.developster.domain.post.comment.main.dto.summary.CommentsSummariesDto;
import com.example.developster.domain.post.comment.main.dto.summary.CommentSummariesDetailDto;
import com.example.developster.domain.post.comment.main.dto.summary.RepliesSummariesDetailDto;
import com.example.developster.domain.post.comment.main.dto.summary.RepliesSummariesDto;
import com.example.developster.domain.post.comment.main.dto.request.CommentCreateRequestDto;
import com.example.developster.domain.post.comment.main.dto.response.CommentCreateResponseDto;
import com.example.developster.domain.post.comment.main.service.CommentService;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.constants.AuthConstants;
import com.example.developster.global.exception.code.SuccessCode;
import com.example.developster.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "게시물 댓글 API",
        description = "게시물 댓글 관련 API"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/comments")
public class CommentController{

    private final CommentService commentService;

    @Operation(
            summary = "댓글 생성",
            description = "새로운 댓글을 생성합니다."
    )
    @PostMapping
    public ResponseEntity<CommonResponse<CommentCreateResponseDto>> createComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentCreateRequestDto dto,
            @SessionAttribute(AuthConstants.LOGIN_USER) User loginUser
    ){

        CommentCreateResponseDto resDto =  commentService.createComment(dto,postId, loginUser);
        return CommonResponse.success(SuccessCode.SUCCESS_INSERT,resDto);
    }

    @Operation(
            summary = "댓글 조회",
            description = "한 게시물의 댓글을 모두 조회합니다."
    )
    @GetMapping
    public ResponseEntity<CommonResponse<CommentsSummariesDto>> readComments(
            @PathVariable Long postId,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size,
            @SessionAttribute(AuthConstants.LOGIN_USER) User loginUser

    ){
        CommentSummariesDetailDto resDetail = commentService.readComments(loginUser,postId,lastId, size);
        return CommonResponse.success(SuccessCode.SUCCESS, new CommentsSummariesDto(resDetail));
    }

    @Operation(
            summary = "답글 조회",
            description = "한 댓글의 답글을 모두 조회합니다."
    )
    @GetMapping("/{commentId}")
    public ResponseEntity<CommonResponse<RepliesSummariesDto>> readReplies(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @RequestParam(required = false) Long lastId,
            @RequestParam(defaultValue = "10") int size,
            @SessionAttribute(AuthConstants.LOGIN_USER) User loginUser
    ){
        RepliesSummariesDetailDto resDetail =  commentService.readReplies(loginUser,commentId,lastId,size);
        return CommonResponse.success(SuccessCode.SUCCESS, new RepliesSummariesDto(resDetail));
    }

    @Operation(
            summary = "댓글 혹은 답글 수정",
            description = """
                    자신이 작성한 댓글 혹은 답글을 수정합니다.
                    타인의 댓글을 수정할 수 없습니다.
                    """
    )
    @PutMapping("/{commentId}")
    public ResponseEntity<CommonResponse<CommentUpdateResponseDto>> updateComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @SessionAttribute(AuthConstants.LOGIN_USER) User user,
            @Valid @RequestBody CommentUpdateRequestDto dto
    ){
        CommentUpdateResponseDto resDto = commentService.updateComment(commentId,user,dto);
        return CommonResponse.success(SuccessCode.SUCCESS_UPDATE, resDto);
    }

    @Operation(
            summary = "댓글 삭제",
            description = """
                    자신이 작성한 댓글 혹은 답글을 삭제합니다.
                    타인의 댓글을 삭제할 수 없습니다.
                    """
    )
    @DeleteMapping("/{commentId}")
    public ResponseEntity<CommonResponse<Void>> deleteComment(
            @PathVariable Long postId,
            @PathVariable Long commentId,
            @SessionAttribute(AuthConstants.LOGIN_USER) User loginUser
    ){
        commentService.deleteComment(commentId, loginUser);
        return CommonResponse.success(SuccessCode.SUCCESS_DELETE);
    }
}
