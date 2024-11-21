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
    WRONG_CONDITION_PASSWORD(HttpStatus.BAD_REQUEST, "영문, 숫자, 특수문자를 최소 1글자씩 포함해야 하며 8글자 이상이어야 합니다."),
    WRONG_CONDITION_EMAIL(HttpStatus.BAD_REQUEST, "형식에 맞지 않는 이메일입니다."),
    SAME_PASSWORD(HttpStatus.BAD_REQUEST, "기존 비밀번호와 새로운 비밀번호가 동일합니다."),
    EMPTY_PASSWORD(HttpStatus.BAD_REQUEST, "기존 비밀번호와 새로운 비밀번호를 모두 입력해주세요."),
    ALREADY_DELETED_USER(HttpStatus.BAD_REQUEST, "이미 삭제된 유저 입니다."),


    /**
     * Follow
     */
    NOT_FOUND_FOLLOW(HttpStatus.BAD_REQUEST, "팔로우하지 않은 유저입니다."),


    /**
     * Post
     */
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "게시물 정보를 찾을 수 없습니다."),
    ALREADY_DELETED_POST(HttpStatus.BAD_REQUEST, "이미 삭제된 게시물 입니다."),
    INVALID_REPEAT_TYPE(HttpStatus.BAD_REQUEST, "잘못된 일정 주기 입력입니다"),
    NOT_POST_WRITER(HttpStatus.UNAUTHORIZED, "게시물 작성자가 아닙니다."),


    /**
     * Post Like
     */
    ALREADY_LIKED_POST(HttpStatus.BAD_REQUEST, "이미 좋아요를 한 게시글 입니다."),
    NOT_FOUND_POST_LIKE(HttpStatus.BAD_REQUEST, "아직 좋아요를 하지 않았습니다."),

    /**
     * Post Bookmark
     */
    ALREADY_BOOKMARKED_POST(HttpStatus.BAD_REQUEST, "이미 저장을 한 게시글 입니다."),
    NOT_FOUND_POST_BOOKMARK(HttpStatus.BAD_REQUEST, "아직 저장을 하지 않았습니다."),



    /**
     * Comment
     */
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."),
    NOT_COMMENT_WRITER(HttpStatus.UNAUTHORIZED, "댓글 작성자가 아닙니다."),
    NOT_FOUND_COMMENT_LIKE(HttpStatus.BAD_REQUEST, "댓글에 좋아요를 하지 않았습니다."),
    ;


    private final HttpStatus httpStatus;
    private final String message;
}
