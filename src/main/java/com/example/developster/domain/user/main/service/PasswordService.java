package com.example.developster.domain.user.main.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PasswordService {

    private final BCrypt.Hasher hasher;
    private final BCrypt.Verifyer verifyer;

    public PasswordService(BCrypt.Hasher hasher, BCrypt.Verifyer verifyer) {
        this.hasher = hasher;
        this.verifyer = verifyer;
    }

    public String encode(String rawPassword) {
        return hasher.hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    public boolean matches(String rawPassword, String encodedPassword) {
        BCrypt.Result result = verifyer.verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }

    public boolean validatePassword(@NotEmpty String password) {
        // 비밀번호 길이가 8글자 이상인지 확인
        if (password.length() < 8) {
            return true;
        }

        // 영문, 숫자, 특수문자가 각각 최소 1개 이상 포함되어 있는지 확인하는 정규식
        String regex = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).+$";

        // 정규식 패턴을 사용하여 비밀번호 검증
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return !matcher.matches();
    }
}