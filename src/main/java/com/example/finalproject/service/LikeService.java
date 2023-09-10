package com.example.finalproject.service;

import com.example.finalproject.domain.dto.alarm.AlarmType;
import com.example.finalproject.domain.dto.like.LikeResponse;
import com.example.finalproject.domain.entity.Alarm;
import com.example.finalproject.domain.entity.Like;
import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;
import com.example.finalproject.exception.AppException;
import com.example.finalproject.exception.ErrorCode;
import com.example.finalproject.respository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
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

    // 좋아요를 누르지 않았다면 등록, 눌렀었다면 취소
    @Transactional
    public LikeResponse addOrRemoveLike(Long postId, String username) {
        User user = checkIfUserExists(username);
        Post post = checkIfPostExists(postId);

        Optional<Like> postLike = likeRepository.findByUserAndPost(user, post);

        // likeRepository에서 해당 포스트에 유저 아이디가 중복되는 사람이 있는지 체크해야한다
        likeRepository.findByUserAndPost(user, post).ifPresentOrElse(
                like -> {likeRepository.delete(like);},
                () -> likeRepository.save(new Like(LocalDateTime.now(), post, user))
        );

        LikeResponse likeResponse = new LikeResponse(postLike);

        Alarm alarm = new Alarm(AlarmType.NEW_LIKE, user.getUsername() + "가 좋아요를 눌렀습니다",
                LocalDateTime.now(), user, post.getUser().getUserId(), user.getUserId());
        alarmRepository.save(alarm);

        return likeResponse;
    }



    // 좋아요 갯수
    public Long countLikes(Long postId) {
        Post post = checkIfPostExists(postId);

        Long count = likeRepository.countByPost(post);
        return count;
    }
}

