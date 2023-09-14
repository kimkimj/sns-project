package com.example.finalproject.domain.entity;

import com.example.finalproject.domain.dto.post.PostWriteAndUpdateRequest;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long postId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String title;
    private String body;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    public Post(User user, String title, String body, LocalDateTime createdAt, LocalDateTime lastModifiedAt) {
        this.user = user;
        this.title = title;
        this.body = body;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }

    public void update(PostWriteAndUpdateRequest request) {
        this.title = request.getTitle();
        this.body = request.getBody();
    }
}
