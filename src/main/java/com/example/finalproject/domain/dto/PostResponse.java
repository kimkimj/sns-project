package com.example.finalproject.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostResponse {
    private String message;
    private Long postId;
}
