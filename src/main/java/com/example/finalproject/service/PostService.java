package com.example.finalproject.service;

import com.example.finalproject.domain.Response;
import com.example.finalproject.domain.dto.post.PostRequest;
import com.example.finalproject.domain.dto.post.PostResponse;
import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.respository.PostRepository;
import com.example.finalproject.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
}
