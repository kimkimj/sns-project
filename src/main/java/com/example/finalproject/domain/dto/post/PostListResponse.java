package com.example.finalproject.domain.dto.post;


import com.example.finalproject.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class PostListResponse {
    private List<PostGetResponse> list = new ArrayList<>();

    public PostListResponse(Page<Post> posts) {
        list = posts.stream().map(
                post -> new PostGetResponse(
                        post.getPostId(),
                        post.getUser().getUsername(),
                        post.getTitle(), post.getBody(),
                        post.getCreatedAt(), post.getLastModifiedAt()))
                .collect(Collectors.toList());
    }
}

