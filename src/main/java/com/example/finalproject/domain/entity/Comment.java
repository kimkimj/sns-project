package com.example.finalproject.domain.entity;

import com.example.finalproject.domain.dto.comment.CommentRequest;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Comment(LocalDateTime createdAt, LocalDateTime last_modifiedAt, String comment, Post post, User user) {
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.comment = comment;
        this.post = post;
        this.user = user;
    }

    public void update(String comment) {
        this.comment = comment;
        lastModifiedAt = LocalDateTime.now();
    }
}

