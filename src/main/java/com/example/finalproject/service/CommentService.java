package com.example.finalproject.service;

import com.example.finalproject.domain.dto.comment.*;
import com.example.finalproject.domain.entity.*;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.respository.AlarmRepository;
import com.example.finalproject.respository.CommentRepository;
import com.example.finalproject.respository.PostRepository;
import com.example.finalproject.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final AlarmRepository alarmRepository;

    private User checkIfUserExists(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        return user;
    }

    private Post checkIfPostExists(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        return post;
    }

    private Comment checkForCommentAndUserPermission(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));

        // 댓글의 작성자가 맞는지
        if (!(comment.getUser().getUsername().equals(username))) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }
        return comment;
    }

    public CommentListResponse getAllComments(Long postId, Pageable pageable) {
        Post post = checkIfPostExists(postId);
        Page<Comment> list = commentRepository.findAll(pageable);
        List<CommentResponse> commentListResponse = list.map(lists -> CommentResponse.builder()
                        .id(lists.getCommentId())
                        .comment(lists.getComment())
                        .userName(lists.getUser().getUsername())
                        .postId(lists.getPost().getPostId())
                        .createdAt(lists.getCreated_at())
                        .build())
                .toList();

        return CommentListResponse.builder()
                .list(commentListResponse)
                .build();
    }

    public CommentResponse writeComment(CommentRequest commentRequest, String username, Long postId) {
        User user = checkIfUserExists(username);
        Post post = checkIfPostExists(postId);

        Comment comment = commentRequest.toEntity(user, post);

        Comment savedComment = commentRepository.save(comment);

        CommentResponse commentResponse = CommentResponse.builder()
                .id(savedComment.getCommentId())
                .comment(savedComment.getComment())
                .postId(savedComment.getPost().getPostId())
                .userName(savedComment.getPost().getUser().getUsername())
                .createdAt(savedComment.getCreated_at())
                .build();

        Alarm alarm = Alarm.builder()
                .alarmType("NEW_COMMENT_ON_POST")
                .text("new comment!")
                .user(post.getUser())
                .targetUser(post.getUser().getUserId())
                .fromUser(user.getUserId())
                .registeredAt(LocalDateTime.now())
                .lastModifiedAt(LocalDateTime.now())
                .build();
        alarmRepository.save(alarm);

        return commentResponse;
    }

    public CommentUpdateResponse editComment(CommentRequest commentRequest, String username, Long postId, Long commentId) {;
        User user = checkIfUserExists(username);
        Post post = checkIfPostExists(postId);

        // 댓글이 존재하고 댓글의 작성자가 맞다면 comment로 가져오기
        Comment comment = checkForCommentAndUserPermission(commentId, username);

        comment.setComment(commentRequest.getComment());
        comment.setLast_modified_at(LocalDateTime.now());

        Comment savedComment = commentRepository.saveAndFlush(comment);

        CommentUpdateResponse commentUpdateResponse = CommentUpdateResponse.builder()
                .id(savedComment.getCommentId())
                .comment(savedComment.getComment())
                .userName(savedComment.getUser().getUsername())
                .postId(savedComment.getPost().getPostId())
                .createdAt(savedComment.getCreated_at())
                .lastModifiedAt(savedComment.getLast_modified_at())
                .build();
        return commentUpdateResponse;
    }

    public CommentDeleteResponse deleteComment(String username, Long postId, Long commentId) {
        User user = checkIfUserExists(username);
        Post post = checkIfPostExists(postId);

        // 댓글이 존재하고 댓글의 작성자가 맞다면 comment로 가져오기
        Comment comment = checkForCommentAndUserPermission(commentId, username);

        commentRepository.deleteById(comment.getCommentId());

        return CommentDeleteResponse.builder()
                .message("댓글 삭제 완료")
                .id(comment.getCommentId())
                .build();
    }
}
