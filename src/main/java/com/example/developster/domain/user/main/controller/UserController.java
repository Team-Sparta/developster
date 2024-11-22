package com.example.developster.domain.user.main.controller;

import com.example.developster.domain.user.main.dto.request.LoginRequestDto;
import com.example.developster.domain.user.main.dto.request.UserCreateRequestDto;
import com.example.developster.domain.user.main.dto.request.UserDeleteRequestDto;
import com.example.developster.domain.user.main.dto.request.UserUpdateRequestDto;
import com.example.developster.domain.user.main.dto.response.UserDeleteResponseDto;
import com.example.developster.domain.user.main.dto.response.UserIdResponseDto;
import com.example.developster.domain.user.main.dto.response.UserResponseDto;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.domain.user.main.service.UserService;
import com.example.developster.global.constants.AuthConstants;
import com.example.developster.global.exception.code.SuccessCode;
import com.example.developster.global.response.CommonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(
        name = "회원 API",
        description = "로그인, 회원가입, 프로필 수정 관련 API"
)
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(
            summary = "회원가입"
    )
    @PostMapping("/sign-up")
    public ResponseEntity<CommonResponse<UserResponseDto>> signUpUser(@Validated @RequestBody UserCreateRequestDto userRequestDto) {

        return CommonResponse.success(SuccessCode.SUCCESS_INSERT, userService.createUser(userRequestDto));
    }

    @Operation(
            summary = "로그인"
    )
    @PostMapping("/login")
    public ResponseEntity<CommonResponse<UserIdResponseDto>> loginUser(@Validated @RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        User loginedUser = userService.loginUser(loginRequestDto);

        HttpSession session = request.getSession();

        session.setAttribute(AuthConstants.LOGIN_USER, loginedUser);

        return CommonResponse.success(SuccessCode.SUCCESS, new UserIdResponseDto(loginedUser.getId()));
    }

    @Operation(
            summary = "내정보 조회"
    )
    @GetMapping
    public ResponseEntity<CommonResponse<UserResponseDto>> getUser(@Parameter(hidden = true) @SessionAttribute(AuthConstants.LOGIN_USER) User user) {

        return CommonResponse.success(SuccessCode.SUCCESS, UserResponseDto.toDto(user));
    }


    @Operation(
            summary = "프로필 수정"
    )
    @PatchMapping
    public ResponseEntity<CommonResponse<UserIdResponseDto>> updateUser(@Validated @RequestBody UserUpdateRequestDto userUpdateRequestDto,
                                                                        @Parameter(hidden = true) @SessionAttribute(AuthConstants.LOGIN_USER) User user) {

        return CommonResponse.success(SuccessCode.SUCCESS_UPDATE, userService.updateUser(userUpdateRequestDto, user));
    }

    @Operation(
            summary = "회원 탈퇴"
    )
    @DeleteMapping
    public ResponseEntity<CommonResponse<UserDeleteResponseDto>> deleteUser(@RequestBody UserDeleteRequestDto userDeleteRequestDto,
                                                                            @Parameter(hidden = true) @SessionAttribute(AuthConstants.LOGIN_USER) User user) {

        return CommonResponse.success(SuccessCode.SUCCESS_DELETE, userService.delete(userDeleteRequestDto, user));
    }

}
