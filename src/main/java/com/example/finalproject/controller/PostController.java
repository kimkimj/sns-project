package com.example.finalproject.controller;

import com.example.finalproject.domain.Response;
import com.example.finalproject.domain.dto.post.PostRequest;
import com.example.finalproject.domain.dto.post.PostResponse;
import com.example.finalproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @PostMapping()
    public Response<PostResponse> writeReview(@RequestBody PostRequest postRequest, Authentication authentication) {
        PostResponse postResponse = postService.create(postRequest,authentication.getName());
        return Response.success(postResponse);
    }


}
