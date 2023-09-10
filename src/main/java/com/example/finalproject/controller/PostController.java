package com.example.finalproject.controller;

import com.example.finalproject.domain.Response;
import com.example.finalproject.domain.dto.post.*;
import com.example.finalproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostService postService;

    @PostMapping()
    public Response<PostWriteAndUpdateResponse> writePost(@RequestBody PostWriteAndUpdateRequest postRequest, @ApiIgnore Authentication authentication) {
        PostWriteAndUpdateResponse postWriteResponse = postService.create(postRequest,authentication.getName());
        return Response.success(postWriteResponse);
    }

    @GetMapping("/{postId}")
    public Response<PostGetResponse> getPost(@PathVariable Long postId) {
        PostGetResponse postGetResponse = postService.getPostDetail(postId);
        return Response.success(postGetResponse);
    }

    @GetMapping("")
    public Response<PostListResponse> getAllPosts(@ApiIgnore Pageable pageable) {
        return Response.success(postService.getAll(pageable));
    }

    @PutMapping("/{postId}")
    public Response<PostWriteAndUpdateResponse> update(@PathVariable Long postId,
                                                       @RequestBody PostWriteAndUpdateRequest updateRequest,
                                                       @ApiIgnore Authentication authentication) {

        PostWriteAndUpdateResponse postResponse = postService.update(postId, updateRequest, authentication.getName());
        return Response.success(postResponse);

    }

    @DeleteMapping("/{postId}")
    public Response<PostWriteAndUpdateResponse> delete(@PathVariable Long postId, @ApiIgnore Authentication authentication) {
        PostWriteAndUpdateResponse postResponse = postService.delete(postId, authentication.getName());
        return Response.success(postResponse);
    }

    @GetMapping("/my")
    public Response<PostListResponse> myPage(@ApiIgnore Authentication authentication, @ApiIgnore Pageable pageable) {
        PostListResponse postListResponse = postService.getAllByUser(authentication.getName());
        return Response.success(postListResponse);
    }

}

