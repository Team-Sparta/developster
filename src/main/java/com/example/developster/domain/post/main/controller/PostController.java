package com.example.developster.domain.post.main.controller;

import com.example.developster.domain.post.main.dto.request.WritePostRequest;
import com.example.developster.domain.post.main.dto.response.PostIdResponse;
import com.example.developster.domain.post.main.dto.response.PostListResponse;
import com.example.developster.domain.post.main.dto.response.PostResponse;
import com.example.developster.domain.post.main.enums.PostOrderType;
import com.example.developster.domain.post.main.service.PostService;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.constants.AuthConstants;
import com.example.developster.global.exception.code.SuccessCode;
import com.example.developster.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RequiredArgsConstructor
@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<CommonResponse<PostIdResponse>> createPost(
            @Validated @RequestBody WritePostRequest requestDto,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user
    ) {
        return CommonResponse.success(SuccessCode.SUCCESS_INSERT, postService.createPost(user, requestDto));
    }

    /**
     * Fetch posts with optional sorting and date range filtering.
     *
     * @param lastPostId Optional lastPostId for indexing (1)
     * @param pageSize   Required page size for filtering (10)
     * @param startDate  Optional start date for ordering (yyyy-MM-dd)
     * @param endDate    Optional end date for ordering (yyyy-MM-dd)
     * @param orderBy    Optional Sorting criteria: "UPDATED_DATE" or "LIKE_COUNT"
     * @return List of PostResponse
     */
    @GetMapping
    public ResponseEntity<CommonResponse<PostListResponse>> loadPostList(
            @RequestParam(required = false) Long lastPostId,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) PostOrderType orderBy,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user
    ) {
        return CommonResponse.success(SuccessCode.SUCCESS, postService.loadPostList(user, lastPostId, pageSize, orderBy, startDate, endDate));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<CommonResponse<PostResponse>> loadPost(
            @PathVariable Long postId,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user
    ) {
        return CommonResponse.success(SuccessCode.SUCCESS, postService.loadPost(user, postId));
    }

    @PutMapping("/{postId}")
    public ResponseEntity<CommonResponse<PostIdResponse>> updatePost(
            @PathVariable Long postId,
            @Validated @RequestBody WritePostRequest requestDto,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user
    ) {
        return CommonResponse.success(SuccessCode.SUCCESS_UPDATE, postService.updatePost(user.getId(), requestDto, postId));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<CommonResponse<PostIdResponse>> deletePost(
            @PathVariable Long postId,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user
    ) {
        return CommonResponse.success(SuccessCode.SUCCESS_DELETE, postService.deletePost(user.getId(), postId));
    }
}
