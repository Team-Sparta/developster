package com.example.developster.global.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public abstract class AuthConstants {
    public static final String CODE = "code";
    public static final String REDIRECT_URI = "redirectUri";

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String LOGIN_USER = "loginUser";
}