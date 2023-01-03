package com.example.finalproject.controller;

import com.example.finalproject.domain.Response;
import com.example.finalproject.domain.dto.comment.CommentDeleteResponse;
import com.example.finalproject.domain.dto.comment.CommentRequest;
import com.example.finalproject.domain.dto.comment.CommentResponse;
import com.example.finalproject.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class CommentController {

    private final PostService postService;

    /* 댓글 조회
    @GetMapping("/{postId}/comments")
    public Response<CommentResponse> getCommentsById(@PathVariable Long postId) {

    }*/

    // 댓글 작성
    @PostMapping("/{postId}/comments")
    public Response<CommentResponse> create(@PathVariable Long postId,
                                            @RequestBody CommentRequest commentRequest,
                                            Authentication authentication) {

        CommentResponse commentResponse = postService.writeComment(commentRequest, authentication.getName(), postId);
        return Response.success(commentResponse);
    }

    // 댓글 수정
    @PutMapping("/{postId}/comments/{id}")
    public Response<CommentResponse> edit(@PathVariable Long postId,
                                          @PathVariable Long id,
                                          @RequestBody CommentRequest commentRequest,
                                          Authentication authentication) {
        CommentResponse commentResponse = postService.editComment(commentRequest, authentication.getName(), postId, id);
        return Response.success(commentResponse);
    }

    // 댓글 삭제
    // possible 오류: postsId (복수형)
    @DeleteMapping("/{postsId}/comments/{id}")
    public Response<CommentDeleteResponse> edit(@PathVariable Long postsId,
                                          @PathVariable Long id,
                                          Authentication authentication) {
        CommentDeleteResponse commentDeleteResponse = postService.deleteComment(authentication.getName(), postsId, id);
        return Response.success(commentDeleteResponse);
    }

}
