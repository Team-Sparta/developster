package com.example.developster.domain.user.main.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.example.developster.domain.user.main.dto.LoginRequestDto;
import com.example.developster.domain.user.main.dto.UserRequestDto;
import com.example.developster.domain.user.main.dto.UserResponseDto;
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
    public UserResponseDto createUser(UserRequestDto req) {


        if (validatePassword(req.getPassword())) {
            throw new InvalidParamException(ErrorCode.WRONG_CONDITION_PASSWORD);
        }

        if (validateEmail(req.getEmail())) {
            throw new InvalidParamException(ErrorCode.WRONG_CONDITION_EMAIL);
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
        if (user == null || matches(req.getPassword(), user.getPassword())) {
           throw new InvalidParamException(ErrorCode.INVALID_AUTHENTICATION);
        }

        return user;
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





