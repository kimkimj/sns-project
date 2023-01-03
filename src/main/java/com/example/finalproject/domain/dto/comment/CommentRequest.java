package com.example.finalproject.domain.dto.comment;

import com.example.finalproject.domain.entity.Comment;
import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private String comment;

    public Comment toEntity(User user, Post post) {
        return Comment.builder()
                .created_at(LocalDateTime.now())
                .last_modified_at(LocalDateTime.now())
                .comment(comment)
                .post(post)
                .user(user)
                .build();
    }
}