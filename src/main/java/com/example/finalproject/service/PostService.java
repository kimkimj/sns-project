package com.example.finalproject.service;

import com.example.finalproject.domain.dto.post.PostGetResponse;
import com.example.finalproject.domain.dto.post.PostListResponse;
import com.example.finalproject.domain.dto.post.PostWriteAndUpdateRequest;
import com.example.finalproject.domain.dto.post.PostWriteAndUpdateResponse;
import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.respository.PostRepository;
import com.example.finalproject.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    private User checkIfUserExists(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));

        return user;
    }

    private void checkForAuthorization(String username, String author) {
        if (username != author) {
            throw new AppException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());
        }
    }

    private Post checkIfPostExists(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new AppException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        return post;
    }

    @Transactional
    public PostWriteAndUpdateResponse create(PostWriteAndUpdateRequest postRequest, String username) {
        User user = checkIfUserExists(username);
        Post post = postRepository.save(postRequest.toEntity(user));
        PostWriteAndUpdateResponse postResponse = new PostWriteAndUpdateResponse("포스트 등록 완료", post.getPostId());
        return postResponse;
    }

    public PostGetResponse getPostDetail(Long postId) {
        Post post = checkIfPostExists(postId);
        return new PostGetResponse(postId, post.getUser().getUsername(),
                post.getTitle(), post.getBody(), post.getCreatedAt(),
                post.getLastModifiedAt());
    }

    @Transactional
    public PostWriteAndUpdateResponse update(Long postId, PostWriteAndUpdateRequest postRequest, String username) {
        User user = checkIfUserExists(username);

        Post post = checkIfPostExists(postId);
        checkForAuthorization(user.getUsername(), post.getUser().getUsername());

        post.update(postRequest);

        PostWriteAndUpdateResponse postResponse = new PostWriteAndUpdateResponse("포스트 수정 완료", postId);
        return postResponse;
    }

    @Transactional
    public PostWriteAndUpdateResponse delete(Long postId, String username) {
        User user = checkIfUserExists(username);

        Post post = checkIfPostExists(postId);
        checkForAuthorization(user.getUsername(), post.getUser().getUsername());

        postRepository.deleteById(postId);
        PostWriteAndUpdateResponse postResponse = new PostWriteAndUpdateResponse("포스트 삭제 완료", postId);
        return postResponse;
    }

    public PostListResponse getAll(Pageable pageable) {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> posts = postRepository.findAll(pageRequest);
        return new PostListResponse(posts);
    }

    public PostListResponse getAllByUser(String username) {
        User user = checkIfUserExists(username);
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Post> posts = postRepository.findAllByUser(user, pageRequest);
        return new PostListResponse(posts);
    }
}
