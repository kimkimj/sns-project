package com.example.finalproject.controller;

import com.example.finalproject.domain.Response;
import com.example.finalproject.domain.dto.post.PostGetResponse;
import com.example.finalproject.domain.dto.post.PostListResponse;
import com.example.finalproject.domain.dto.post.PostRequest;
import com.example.finalproject.domain.dto.post.PostResponse;
import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @PostMapping()
    public Response<PostResponse> writePost(@RequestBody PostRequest postRequest, Authentication authentication) {
        PostResponse postResponse = postService.create(postRequest,authentication.getName());
        return Response.success(postResponse);
    }

    @GetMapping("/{postId}")
    public Response<PostGetResponse> getPost(@PathVariable Long postId) {
        Post post = postService.findById(postId);

        PostGetResponse postGetResponse = new PostGetResponse(post.getPostId(),
                post.getTitle(), post.getBody(), post.getUser().getUsername(),
                post.getCreatedAt(), post.getLastModifiedAt());
        return Response.success(postGetResponse);
    }

    @GetMapping("")
    public Response<PostListResponse> getAllPosts(Pageable pageable) {
        Page<Post> posts = postService.getAll(pageable);
        return Response.success(new PostListResponse(posts));
    }

}
