package com.example.finalproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 아이디 입니다"),
    USERNAME_NOT_FOUND(HttpStatus.NOT_FOUND, "아이디를 찾을 수 없습니다"),
    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "권한이 없습니다"),

    INVALID_PERMISSION(HttpStatus.UNAUTHORIZED, "사용자가 권한이 없습니다."),

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "포스트를 찾을 수 없습니다"),

    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다"),

    DUPLICATED_LIKES(HttpStatus.CONFLICT, "이미 좋아요를 눌렀습니다.");

    private HttpStatus httpStatus;
    private String message;
}
