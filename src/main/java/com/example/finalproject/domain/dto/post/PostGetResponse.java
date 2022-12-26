package com.example.finalproject.domain.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostGetResponse {
    private Long id;
    private String title;
    private String body;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
}
