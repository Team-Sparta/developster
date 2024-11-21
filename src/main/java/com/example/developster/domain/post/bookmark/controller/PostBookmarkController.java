package com.example.developster.domain.post.bookmark.controller;

import com.example.developster.domain.post.bookmark.service.PostBookmarkService;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.constants.AuthConstants;
import com.example.developster.global.exception.code.SuccessCode;
import com.example.developster.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "게시물 북마크",
        description = "게시물 북마크 관련 API"
)
@Controller
@RequiredArgsConstructor
@RequestMapping("/posts/{postId}/bookmarks")
public class PostBookmarkController {

    private final PostBookmarkService bookmarkService;

    @Operation(
            summary = "게시물 북마크 저장",
            description = "게시물 북마크에 저장한다"
    )
    @PostMapping
    public ResponseEntity<CommonResponse<Object>> savePost(
            @PathVariable Long postId,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user) {

        bookmarkService.savePost(user, postId);

        return CommonResponse.success(SuccessCode.SUCCESS_INSERT);
    }

    @Operation(
            summary = "게시물 북마크 취소",
            description = "게시물 북마크에서 삭제한다"
    )
    @DeleteMapping
    public ResponseEntity<CommonResponse<Object>> unSavePost(
            @PathVariable Long postId,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user) {

        bookmarkService.unSavePost(user, postId);

        return CommonResponse.success(SuccessCode.SUCCESS_DELETE);
    }

}
