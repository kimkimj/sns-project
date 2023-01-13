package com.example.finalproject.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Response<T> {
    private String resultCode;
    private T result;

    // 에러가 나면 코드와 null을 constructor에 넣어 생성
    public static Response<Void> error(String resultCode) {
        return new Response(resultCode, null);
    }

    // 성공하면 result code 로 success와 결과값을 constructor에 넣어 생성
    public static <T> Response<T> success(T result) {
        return new Response("SUCCESS", result);
    }
}

