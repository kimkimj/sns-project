package com.example.finalproject.service;

import com.example.finalproject.domain.dto.post.PostGetResponse;
import com.example.finalproject.domain.dto.post.PostListResponse;
import com.example.finalproject.domain.dto.post.PostRequest;
import com.example.finalproject.domain.dto.post.PostResponse;
import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
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


    public List<PostGetResponse> getAll(Pageable pageable) {
        Page<Post> posts = postRepository.findAll(pageable);
        List<PostGetResponse> list = new ArrayList<>();
        for (Post post: posts) {
            PostGetResponse.builder()
                    .id(post.getPostId())
                    .title(post.getTitle())
                    .body(post.getBody())
                    .userName(post.getUser().getUsername())
                    .createdAt(post.getCreatedAt())
                    .lastModifiedAt(post.getLastModifiedAt())
                    .build();
        }
        return list;
    }
}
