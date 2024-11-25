package com.example.developster.domain.user.main.service;

import com.example.developster.domain.user.main.dto.request.LoginRequestDto;
import com.example.developster.domain.user.main.dto.request.UserCreateRequestDto;
import com.example.developster.domain.user.main.dto.request.UserDeleteRequestDto;
import com.example.developster.domain.user.main.dto.request.UserUpdateRequestDto;
import com.example.developster.domain.user.main.dto.response.UserDeleteResponseDto;
import com.example.developster.domain.user.main.dto.response.UserIdResponseDto;
import com.example.developster.domain.user.main.dto.response.UserResponseDto;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.domain.user.main.repository.UserRepository;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;

    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto req) {

        if (!validateEmail(req.email())) {
            throw new InvalidParamException(ErrorCode.WRONG_CONDITION_EMAIL);
        }

        if (passwordService.validatePassword(req.password())) {
            throw new InvalidParamException(ErrorCode.WRONG_CONDITION_PASSWORD);
        }

        User user = userRepository.findByEmail(req.email());

        if (user != null) {
            throw new InvalidParamException(ErrorCode.EXIST_EMAIL);
        }

        User savedUser = User.builder()
                .name(req.name())
                .email(req.email())
                .password(passwordService.encode(req.password()))
                .bio(req.bio())
                .profile(req.profile()).build();

        User save = userRepository.save(savedUser);

        return UserResponseDto.toDto(save);
    }


    public User loginUser(LoginRequestDto req) {
        User user = userRepository.findByEmail(req.getEmail());
        if (user == null || !passwordService.matches(req.getPassword(), user.getPassword())) {
            throw new InvalidParamException(ErrorCode.INVALID_AUTHENTICATION);
        }
        return user;
    }

    @Transactional
    public UserIdResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto, User user) {
        if (userUpdateRequestDto.getName() == null) {
            throw new InvalidParamException(ErrorCode.NONE_NAME);
        }

        if (userUpdateRequestDto.getCurrentPassword() == null && userUpdateRequestDto.getNewPassword() == null) {
            user.update(userUpdateRequestDto);
            userRepository.saveAndFlush(user);

            return new UserIdResponseDto(user.getId());
        } else if (userUpdateRequestDto.getCurrentPassword() == null || userUpdateRequestDto.getNewPassword() == null) {
            throw new InvalidParamException(ErrorCode.EMPTY_PASSWORD);
        }

        if (!passwordService.matches(userUpdateRequestDto.getCurrentPassword(), user.getPassword())) {
            throw new InvalidParamException(ErrorCode.INVALID_AUTHENTICATION);

        }
        if (passwordService.matches(userUpdateRequestDto.getNewPassword(), user.getPassword())) {
            throw new InvalidParamException(ErrorCode.SAME_PASSWORD);
        }

        if (passwordService.validatePassword(userUpdateRequestDto.getNewPassword())) {
            throw new InvalidParamException(ErrorCode.WRONG_CONDITION_PASSWORD);
        }

        user.setPassword(passwordService.encode(userUpdateRequestDto.getNewPassword()));
        user.update(userUpdateRequestDto);

        userRepository.saveAndFlush(user);

        return new UserIdResponseDto(user.getId());
    }


    public UserDeleteResponseDto delete(UserDeleteRequestDto userDeleteRequestDto, User user) {

        if (!passwordService.matches(userDeleteRequestDto.getPassword(), user.getPassword())) {
            throw new InvalidParamException(ErrorCode.INVALID_AUTHENTICATION);
        }

        user.delete();

        return new UserDeleteResponseDto(user.getId());
    }

    public static boolean validateEmail(@NotBlank String email) {
        // 이메일 형식에 대한 정규식
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // 정규식 패턴을 사용하여 이메일 검증
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }

}





