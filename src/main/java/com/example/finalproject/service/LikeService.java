package com.example.finalproject.service;

import com.example.finalproject.domain.dto.like.LikeResponse;
import com.example.finalproject.domain.entity.Like;
import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.respository.CommentRepository;
import com.example.finalproject.respository.LikeRepository;
import com.example.finalproject.respository.PostRepository;
import com.example.finalproject.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

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


    public LikeResponse addLike(Long postId, String username) {
        User user = checkIfUserExists(username);
        Post post = checkIfPostExists(postId);

        // likeRepository에서 해당 포스트에 유저 아이디가 중복되는 사람이 있는지 체크해야한다
        likeRepository.findByUserAndPost(user, post).ifPresent(like -> {
            throw new AppException(ErrorCode.DUPLICATED_LIKES, ErrorCode.DUPLICATED_LIKES.getMessage());
        });

        Like like = Like.builder()
                .created_at(LocalDateTime.now())
                .last_modified_at(LocalDateTime.now())
                .post(post)
                .user(user)
                .build();

        likeRepository.save(like);

        LikeResponse likeResponse = new LikeResponse("좋아요를 눌렀습니다.");

        return likeResponse;
    }

    // 좋아요 갯수
    public Long countLikes(Long postId) {
        Post post = checkIfPostExists(postId);

        Long count = likeRepository.countByPost(post);
        return count;
    }
}
