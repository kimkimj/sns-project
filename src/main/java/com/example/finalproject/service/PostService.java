package com.example.finalproject.service;

import com.example.finalproject.domain.dto.comment.CommentDeleteResponse;
import com.example.finalproject.domain.dto.comment.CommentRequest;
import com.example.finalproject.domain.dto.comment.CommentResponse;
import com.example.finalproject.domain.dto.post.PostGetResponse;
import com.example.finalproject.domain.dto.post.PostListResponse;
import com.example.finalproject.domain.dto.post.PostRequest;
import com.example.finalproject.domain.dto.post.PostResponse;
import com.example.finalproject.domain.entity.Comment;
import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.respository.CommentRepository;
import com.example.finalproject.respository.PostRepository;
import com.example.finalproject.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

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

    public PostResponse create(PostRequest postRequest, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Post post = postRepository.save(postRequest.toEntity(user));
        PostResponse postResponse = new PostResponse("포스트 등록 완료", post.getPostId());
        return postResponse;
    }

    public Post findById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
    }



    public PostResponse update(Long postId, PostRequest postRequest, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        if (!user.getUsername().equals(post.getUser().getUsername())) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        LocalDateTime createdAt = post.getCreatedAt();
        postRepository.deleteById(postId);

        Post updated = new Post();
        updated.setPostId(postId);
        updated.setUser(user);
        updated.setTitle(postRequest.getTitle());
        updated.setBody(postRequest.getBody());
        updated.setCreatedAt(createdAt);
        updated.setLastModifiedAt(LocalDateTime.now());

        postRepository.save(updated);

        PostResponse postResponse = new PostResponse("포스트 수정 완료", postId);
        return postResponse;
    }

    public PostResponse delete(Long postId, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));

        if (!user.getUsername().equals(post.getUser().getUsername())) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        postRepository.deleteById(postId);
        PostResponse postResponse = new PostResponse("포스트 삭제 완료", postId);
        return postResponse;
    }


    public PostListResponse getAll(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        List<PostGetResponse> postList = new ArrayList<>();
        for (Post post: posts) {
            PostGetResponse onePost = PostGetResponse.builder()
                    .id(post.getPostId())
                    .title(post.getTitle())
                    .body(post.getBody())
                    .userName(post.getUser().getUsername())
                    .createdAt(post.getCreatedAt())
                    .lastModifiedAt(post.getLastModifiedAt())
                    .build();

            postList.add(onePost);
        }

        return PostListResponse.builder()
                .list(postList)
                .pageable(pageable)
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

        return commentResponse;
    }

    public CommentResponse editComment(CommentRequest commentRequest, String username, Long postId, Long commentId) {;
        User user = checkIfUserExists(username);
        Post post = checkIfPostExists(postId);

        // 댓글이 존재 하면 comment로 가져오기
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));

        // 댓글의 작성자가 맞는지
        if (!(comment.getUser().getUsername().equals(username))) {
                throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        comment.setComment(commentRequest.getComment());
        comment.setLast_modified_at(LocalDateTime.now());

        Comment savedComment = commentRepository.saveAndFlush(comment);

        CommentResponse commentResponse = CommentResponse.builder()
                .id(savedComment.getCommentId())
                .comment(savedComment.getComment())
                .userName(savedComment.getUser().getUsername())
                .postId(savedComment.getPost().getPostId())
                .createdAt(savedComment.getCreated_at())
                .build();
        return commentResponse;

    }

    public CommentDeleteResponse deleteComment(String username, Long postId, Long commentId) {
        User user = checkIfUserExists(username);
        Post post = checkIfPostExists(postId);

        // 댓글이 존재 하면 comment로 가져오기
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new AppException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));

        // 댓글의 작성자가 맞는지
        if (!(comment.getUser().getUsername().equals(username))) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }

        commentRepository.deleteById(comment.getCommentId());

        return CommentDeleteResponse.builder()
                .message("댓글 삭제 완료")
                .id(comment.getCommentId())
                .build();
    }


}
