package com.example.developster.domain.user.main.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponseDto createUser(UserCreateRequestDto req) {


        if (!validateEmail(req.getEmail())) {
            throw new InvalidParamException(ErrorCode.WRONG_CONDITION_EMAIL);
        }

        if (!validatePassword(req.getPassword())) {
            throw new InvalidParamException(ErrorCode.WRONG_CONDITION_PASSWORD);
        }

        User user = userRepository.findByEmail(req.getEmail());

        if (user != null) {
            throw new InvalidParamException(ErrorCode.EXIST_EMAIL);
        }

        User savedUser = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(encode(req.getPassword()))
                .bio(req.getBio())
                .profile(req.getProfile()).build();

        User save = userRepository.save(savedUser);

        return UserResponseDto.toDto(save);
    }


    public User loginUser(LoginRequestDto req) {
        User user = userRepository.findByEmail(req.getEmail());
        if (user == null || !matches(req.getPassword(), user.getPassword())) {
           throw new InvalidParamException(ErrorCode.INVALID_AUTHENTICATION);
        }
        return user;
    }

    public UserIdResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto, User user) {

        User currentUser = userRepository.findByEmail(user.getEmail());
        if (userUpdateRequestDto.getCurrentPassword() == null && userUpdateRequestDto.getNewPassword() == null) {
            currentUser.update(userUpdateRequestDto);

            return new UserIdResponseDto(user.getId());
        } else if (userUpdateRequestDto.getCurrentPassword() == null || userUpdateRequestDto.getNewPassword() == null) {
            throw new InvalidParamException(ErrorCode.EMPTY_PASSWORD);
        }

        if (!matches(userUpdateRequestDto.getCurrentPassword(), user.getPassword())){
            throw new InvalidParamException(ErrorCode.INVALID_AUTHENTICATION);
        }

        if (matches(userUpdateRequestDto.getNewPassword(), user.getPassword())){
            throw new InvalidParamException(ErrorCode.SAME_PASSWORD);
        }

        if (!validatePassword(userUpdateRequestDto.getNewPassword())) {
            throw new InvalidParamException(ErrorCode.WRONG_CONDITION_PASSWORD);
        }

        currentUser.setPassword(encode(userUpdateRequestDto.getNewPassword()));
        currentUser.update(userUpdateRequestDto);

        return new UserIdResponseDto(user.getId());
    }


    public UserDeleteResponseDto delete(UserDeleteRequestDto userDeleteRequestDto, User user) {

        User reqeustUser = userRepository.findByEmail(user.getEmail());

        if(!matches(userDeleteRequestDto.getPassword(), user.getPassword())) {
            throw new InvalidParamException(ErrorCode.INVALID_AUTHENTICATION);
        }

        reqeustUser.delete();

        return new UserDeleteResponseDto(reqeustUser.getId(), reqeustUser.getDeletedAt());
    }


    public static boolean validatePassword(String password) {
        // 비밀번호 길이가 8글자 이상인지 확인
        if (password.length() < 8) {
            return false;
        }

        // 영문, 숫자, 특수문자가 각각 최소 1개 이상 포함되어 있는지 확인하는 정규식
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$";

        // 정규식 패턴을 사용하여 비밀번호 검증
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static boolean validateEmail(String email) {
        // 이메일 형식에 대한 정규식
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // 정규식 패턴을 사용하여 이메일 검증
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);

        return matcher.matches();
    }



    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}





