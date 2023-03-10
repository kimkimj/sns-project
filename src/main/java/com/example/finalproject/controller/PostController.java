package com.example.finalproject.controller;

import com.example.finalproject.domain.Response;
import com.example.finalproject.domain.dto.post.*;
import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @PostMapping()
    public Response<PostResponse> writePost(@RequestBody PostRequest postRequest, @ApiIgnore Authentication authentication) {
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
    public Response<PostListResponse> getAllPosts(@ApiIgnore Pageable pageable) {
        return Response.success(postService.getAll(pageable));
    }

    @PutMapping("/{postId}")
    public Response<PostResponse> update(@PathVariable Long postId,
                                         @RequestBody PostRequest updateRequest,
                                         @ApiIgnore Authentication authentication) {

        PostResponse postResponse = postService.update(postId, updateRequest, authentication.getName());
        return Response.success(postResponse);

    }

    @DeleteMapping("/{postId}")
    public Response<PostResponse> delete(@PathVariable Long postId, @ApiIgnore Authentication authentication) {
        PostResponse postResponse = postService.delete(postId, authentication.getName());
        return Response.success(postResponse);
    }

    @GetMapping("/my")
    public Response<PostListResponse> myPage(@ApiIgnore Authentication authentication, @ApiIgnore Pageable pageable) {
        PostListResponse postListResponse = postService.getAllByUser(authentication.getName(), pageable);
        return Response.success(postListResponse);
    }

}

