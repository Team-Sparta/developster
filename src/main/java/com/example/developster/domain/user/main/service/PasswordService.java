package com.example.developster.domain.user.main.service;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

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
}