package com.example.developster.global.exception.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * Server
     */
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "유효성 검사 실패"),
    CONSTRAINT_VIOLATION(HttpStatus.CONFLICT, "제약 조건 위반"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "내부 서버 오류가 발생하였습니다."),

    /**
     * Common
     */
    NOT_FOUND_ENUM_CONSTANT(HttpStatus.NOT_FOUND, "열거형 상수값을 찾을 수 없습니다."),
    IS_NULL(HttpStatus.BAD_REQUEST, "NULL 값이 들어왔습니다."),
    COMMON_INVALID_PARAM(HttpStatus.BAD_REQUEST, "요청한 값이 올바르지 않습니다."),
    NO_SUCH_METHOD(HttpStatus.BAD_REQUEST, "메소드를 찾을 수 없습니다."),

    /**
     * AAuthentication
     */
    UNAUTHORIZED_ACCESS(HttpStatus.FORBIDDEN, "로그인을 해주세요."),
    INVALID_AUTHENTICATION(HttpStatus.BAD_REQUEST, "인증이 올바르지 않습니다."),

    /**
     * User
     */
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "회원 정보를 찾을 수 없습니다."),
    EXIST_EMAIL(HttpStatus.BAD_REQUEST, "중복된 이메일입니다."),
    EXIST_USERNAME(HttpStatus.BAD_REQUEST, "중복된 이름입니다."),

    /**
     * Schedule
     */
    NOT_FOUND_SCHEDULE(HttpStatus.NOT_FOUND, "스케줄 정보를 찾을 수 없습니다."),
    ALREADY_DELETED_SCHEDULE(HttpStatus.BAD_REQUEST, "이미 삭제된 스케줄 입니다."),
    INVALID_REPEAT_TYPE(HttpStatus.BAD_REQUEST, "잘못된 일정 주기 입력입니다"),
    NOT_SCHEDULE_WRITER(HttpStatus.UNAUTHORIZED, "스케줄 작성자가 아닙니다."),

    /**
     * Comment
     */
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    NOT_COMMENT_WRITER(HttpStatus.UNAUTHORIZED, "댓글 작성자가 아닙니다."),
    ;


    private final HttpStatus httpStatus;
    private final String message;
}
