package com.example.finalproject.domain.dto.post;

import com.example.finalproject.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostGetResponse {
    private Long id;
    private String username;
    private String title;
    private String body;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public PostGetResponse(Post post) {
        id = post.getPostId();
        username = post.getUser().getUsername();
        title= post.getTitle();
        body = post.getBody();
        createdAt = post.getCreatedAt();
        lastModifiedAt = post.getLastModifiedAt();
    }
}

