package com.example.finalproject.controller;

import com.example.finalproject.domain.Response;
import com.example.finalproject.domain.dto.comment.*;
import com.example.finalproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class CommentRestController {

    private final CommentService commentService;

    @GetMapping("/{postId}/comments")
    public Response<CommentListResponse> getAllComments(@PathVariable Long postId) {

        CommentListResponse commentListResponse = commentService.getAllComments(postId);
        return Response.success(commentListResponse);
    }

    // 댓글 작성
    @PostMapping("/{postId}/comments")
    public Response<CommentResponse> create(@PathVariable Long postId,
                                            @RequestBody CommentRequest commentRequest,
                                            @ApiIgnore Authentication authentication) {

        CommentResponse commentResponse = commentService.writeComment(commentRequest, authentication.getName(), postId);
        return Response.success(commentResponse);
    }

    // 댓글 수정
    @PutMapping("/{postId}/comments/{id}")
    public Response<CommentUpdateResponse> edit(@PathVariable Long postId,
                                          @PathVariable Long id,
                                          @RequestBody CommentRequest commentRequest,
                                          @ApiIgnore Authentication authentication) {
        CommentUpdateResponse commentUpdateResponse = commentService.editComment(commentRequest, authentication.getName(), postId, id);
        return Response.success(commentUpdateResponse);
    }

    // 댓글 삭제
    @DeleteMapping("/{postId}/comments/{id}")
    public Response<CommentDeleteResponse> delete(@PathVariable Long postId,
                                          @PathVariable Long id,
                                                  @ApiIgnore Authentication authentication) {
        CommentDeleteResponse commentDeleteResponse = commentService.deleteComment(authentication.getName(), postId, id);
        return Response.success(commentDeleteResponse);
    }

}

