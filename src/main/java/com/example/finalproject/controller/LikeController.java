package com.example.finalproject.controller;

import com.example.finalproject.domain.Response;
import com.example.finalproject.domain.dto.like.LikeResponse;
import com.example.finalproject.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/posts/{postId}/likes")
    public Response<LikeResponse> clickLike(@PathVariable Long postId, Authentication authentication) {
        LikeResponse likeResponse = likeService.addLike(postId, authentication.getName());
        return Response.success(likeResponse);
    }

    @GetMapping("/posts/{postId}/likes")
    public Response<Long> countLikes(@PathVariable Long postId) {
        Long count = likeService.countLikes(postId);
        return Response.success(count);
    }
}
