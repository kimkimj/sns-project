package com.example.finalproject.domain.dto;

import com.example.finalproject.domain.UserRole;
import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;

public class PostRequest {
    private String title;
    private String body;

    public Post toEntity() {
        return Post.builder()
                .title(this.title)
                .body(body)
                .build();
    }
}
