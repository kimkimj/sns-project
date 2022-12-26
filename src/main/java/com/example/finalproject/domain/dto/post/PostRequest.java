package com.example.finalproject.domain.dto.post;

import com.example.finalproject.domain.UserRole;
import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PostRequest {
    private String title;
    private String body;

    public Post toEntity(User user) {
        return Post.builder()
                .title(this.title)
                .body(body)
                .createdAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .user(user)
                .build();
    }
}
