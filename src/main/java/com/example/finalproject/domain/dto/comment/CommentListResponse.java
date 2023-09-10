package com.example.finalproject.domain.dto.comment;


import com.example.finalproject.domain.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentListResponse {
    private List<CommentResponse> content;

    public CommentListResponse(Page<Comment> comments) {
        content = comments.stream().map(
                c -> new CommentResponse(
                        c.getCommentId(),
                        c.getComment(),
                        c.getUser().getUsername(),
                        c.getPost().getPostId(),
                        c.getCreatedAt()))
                .collect(Collectors.toList());
    }
}

