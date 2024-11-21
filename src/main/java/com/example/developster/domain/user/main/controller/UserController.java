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
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/sign-up")
    public ResponseEntity<CommonResponse<UserResponseDto>> signUpUser(@RequestBody UserCreateRequestDto userRequestDto){

        return CommonResponse.success(SuccessCode.SUCCESS_INSERT, userService.createUser(userRequestDto));
    }

    @PostMapping("/login")
    public ResponseEntity<CommonResponse<UserIdResponseDto>> loginUser(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        User loginedUser = userService.loginUser(loginRequestDto);

        HttpSession session = request.getSession();

        session.setAttribute(AuthConstants.LOGIN_USER, loginedUser);

        return CommonResponse.success(SuccessCode.SUCCESS, new UserIdResponseDto(loginedUser.getId()));
    }

    @GetMapping
    public ResponseEntity<CommonResponse<UserResponseDto>> getUser(@SessionAttribute (AuthConstants.LOGIN_USER) User user) {

        return CommonResponse.success(SuccessCode.SUCCESS, UserResponseDto.toDto(user));
    }



    @PatchMapping
    public ResponseEntity<CommonResponse<UserIdResponseDto>> updateUser(@RequestBody UserUpdateRequestDto userUpdateRequestDto,
                                                                        @SessionAttribute (AuthConstants.LOGIN_USER) User user) {

        return CommonResponse.success(SuccessCode.SUCCESS_UPDATE, userService.updateUser(userUpdateRequestDto, user));
    }


    @DeleteMapping
    public ResponseEntity<CommonResponse<UserDeleteResponseDto>> deleteUser(@RequestBody UserDeleteRequestDto userDeleteRequestDto,
                                                                            @SessionAttribute (AuthConstants.LOGIN_USER) User user) {

        return CommonResponse.success(SuccessCode.SUCCESS_DELETE, userService.delete(userDeleteRequestDto, user));
    }

}
