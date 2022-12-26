package com.example.finalproject.domain.dto.post;


import com.example.finalproject.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@AllArgsConstructor
public class PostListResponse {
    private final Page<Post> posts;
}
