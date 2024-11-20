package com.example.developster.domain.user.main.service;

import com.example.developster.domain.user.main.dto.LoginRequestDto;
import com.example.developster.domain.user.main.dto.UserIdResponseDto;
import com.example.developster.domain.user.main.dto.UserRequestDto;
import com.example.developster.domain.user.main.dto.UserResponsDto;
import com.example.developster.domain.user.main.entity.User;
import com.example.developster.domain.user.main.repository.UserRepository;
import com.example.developster.global.exception.InvalidParamException;
import com.example.developster.global.exception.code.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import java.util.InvalidPropertiesFormatException;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public UserResponsDto createUser(UserRequestDto req) {
        User savedUser = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(req.getPassword())
                .bio(req.getBio())
                .profile(req.getProfile()).build();

        User save = userRepository.save(savedUser);

        return UserResponsDto.toDto(save);
    }


    public User loginUser(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.getEmail());
        if (user == null || !Objects.equals(user.getPassword(), loginRequestDto.getPassword())) {
           throw new InvalidParamException(ErrorCode.INVALID_AUTHENTICATION);
        }

        return user;
    }
}
