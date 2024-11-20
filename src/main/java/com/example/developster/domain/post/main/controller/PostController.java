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
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/post")
public class PostController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<CommonResponse<PostIdResponse>> createPost(
            @Validated @RequestBody WritePostRequest requestDto,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user
    ) {
        return CommonResponse.success(SuccessCode.SUCCESS_INSERT, postService.createPost(user, requestDto));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<PostListResponse>> loadPostList(
            @RequestParam(required = false) Long lastPostId,
            @RequestParam Integer pageSize,
            @RequestParam(required = false) PostOrderType orderBy,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user
    ) {
        return CommonResponse.success(SuccessCode.SUCCESS, postService.loadPostList(user, lastPostId, pageSize, orderBy));
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
