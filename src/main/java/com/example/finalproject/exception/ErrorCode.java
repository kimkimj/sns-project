package com.example.finalproject.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    USERNAME_DUPLICATED(HttpStatus.CONFLICT, "이미 존재하는 아이디 입니다");

    private HttpStatus httpStatus;
    private String message;
}
