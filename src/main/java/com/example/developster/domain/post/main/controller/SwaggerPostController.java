package com.example.developster.domain.post.main.controller;

import com.example.developster.domain.post.main.dto.request.WritePostRequest;
import com.example.developster.domain.post.main.dto.response.PostIdResponse;
import com.example.developster.domain.post.main.dto.response.PostListResponse;
import com.example.developster.domain.post.main.enums.PostOrderType;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.global.constants.AuthConstants;
import com.example.developster.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDate;

@Tag(
        name = "게시물 API",
        description = "게시물 관련 API"
)
public interface SwaggerPostController {

    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "새로운 게시물 생성",
                    content = {@Content(mediaType = "*/*",
                            schema = @Schema(implementation = CommonResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                            {
                                              "success": false,
                                              "message": "Internal Server Error",
                                              "result": {}
                                            }
                                    """))})
    })
    ResponseEntity<CommonResponse<PostIdResponse>> createPost(
            @Validated @RequestBody WritePostRequest requestDto,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user
    );

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Load Post List",
                    content = {@Content(mediaType = "*/*",
                            schema = @Schema(implementation = CommonResponse.class))}),
            @ApiResponse(responseCode = "500", description = "Internal Server Error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(example = """
                                            {
                                              "success": false,
                                              "message": "Internal Server Error",
                                              "result": {}
                                            }
                                    """))})
    })
    ResponseEntity<CommonResponse<PostListResponse>> loadPostList(
            @RequestParam(required = false) Long lastPostId,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) PostOrderType orderBy,
            @RequestParam(value = "startDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(value = "endDate", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @Parameter(hidden = true) @SessionAttribute(value = AuthConstants.LOGIN_USER) User user
    );



}
