package com.example.finalproject.service;

import com.example.finalproject.domain.dto.alarm.AlarmType;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

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

    @Transactional(readOnly = true)
    public CommentListResponse getAllComments(Long postId) {
        Post post = checkIfPostExists(postId);
        PageRequest pageRequest= PageRequest.of(0, 20, Sort.by("createdAt"));
        Page<Comment> list = commentRepository.findAllByPost(post, pageRequest);
        return new CommentListResponse(list);
    }

    @Transactional
    public CommentResponse writeComment(CommentRequest commentRequest, String username, Long postId) {
        User user = checkIfUserExists(username);
        Post post = checkIfPostExists(postId);

        Comment comment = commentRequest.toEntity(user, post);

        Comment savedComment = commentRepository.save(comment);

        CommentResponse commentResponse = new CommentResponse(savedComment.getCommentId(),
                savedComment.getComment(),
                savedComment.getUser().getUsername(),
                savedComment.getPost().getPostId(),
                savedComment.getCreatedAt());

        Alarm alarm = new Alarm(AlarmType.NEW_COMMENT, "새로운 댓글이 달렸습니다", LocalDateTime.now(),
                user, post.getUser().getUserId(), user.getUserId());
        alarmRepository.save(alarm);

        return commentResponse;
    }

    @Transactional
    public CommentUpdateResponse editComment(CommentRequest commentRequest, String username, Long postId, Long commentId) {;
        User user = checkIfUserExists(username);
        Post post = checkIfPostExists(postId);

        // 댓글이 존재하고 댓글의 작성자가 맞다면 comment로 가져오기
        Comment comment = checkForCommentAndUserPermission(commentId, username);

        comment.update(commentRequest.getComment());

        return new CommentUpdateResponse(comment.getCommentId(), comment.getComment(),
                comment.getUser().getUsername(), comment.getPost().getPostId(),
                comment.getCreatedAt(), comment.getLastModifiedAt());
    }

    @Transactional
    public CommentDeleteResponse deleteComment(String username, Long postId, Long commentId) {
        User user = checkIfUserExists(username);
        Post post = checkIfPostExists(postId);

        // 댓글이 존재하고 댓글의 작성자가 맞다면 comment로 가져오기
        Comment comment = checkForCommentAndUserPermission(commentId, username);

        commentRepository.deleteById(comment.getCommentId());

        return new CommentDeleteResponse(commentId, "댓글 삭제 완료");
    }
}

