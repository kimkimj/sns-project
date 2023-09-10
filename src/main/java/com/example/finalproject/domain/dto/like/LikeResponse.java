package com.example.finalproject.domain.dto.like;

import com.example.finalproject.domain.entity.Like;
import lombok.*;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class LikeResponse {
    private String message;

    public LikeResponse(Optional<Like> like) {
        if (like.isEmpty()) {
            message = "좋아요를 눌렀습니다";
        } else {
            message = "좋으요를 취소했습니다";
        }
    }
}

