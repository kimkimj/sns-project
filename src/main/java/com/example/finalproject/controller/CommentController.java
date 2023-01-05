package com.example.finalproject.controller;

import com.example.finalproject.domain.Response;
import com.example.finalproject.domain.dto.comment.CommentDeleteResponse;
import com.example.finalproject.domain.dto.comment.CommentListResponse;
import com.example.finalproject.domain.dto.comment.CommentRequest;
import com.example.finalproject.domain.dto.comment.CommentResponse;
import com.example.finalproject.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class CommentController {

    private final CommentService commentService;


    @GetMapping("/{postId}/comments")
    public Response<CommentListResponse> getAllComments(@PathVariable Long postId, @ApiIgnore Pageable pageable) {

        CommentListResponse commentListResponse = commentService.getAllComments(postId, pageable);
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
    public Response<CommentResponse> edit(@PathVariable Long postId,
                                          @PathVariable Long id,
                                          @RequestBody CommentRequest commentRequest,
                                          @ApiIgnore Authentication authentication) {
        CommentResponse commentResponse = commentService.editComment(commentRequest, authentication.getName(), postId, id);
        return Response.success(commentResponse);
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
