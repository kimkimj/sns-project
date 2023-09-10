package com.example.finalproject.domain.dto.comment;

import com.example.finalproject.domain.entity.Comment;
import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequest {
    private String comment;

    public Comment toEntity(User user, Post post) {
        return new Comment(LocalDateTime.now(), LocalDateTime.now(), comment, post, user);
    }
}
