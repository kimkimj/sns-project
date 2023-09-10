package com.example.finalproject.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostWriteAndUpdateResponse {
    private String message;
    private Long postId;
}

