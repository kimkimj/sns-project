package com.example.finalproject.domain.dto.post;

import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostWriteAndUpdateRequest {
    private String title;
    private String body;

    public Post toEntity(User user) {
        return new Post(user, this.title, this.body, LocalDateTime.now(), LocalDateTime.now());
    }
}

