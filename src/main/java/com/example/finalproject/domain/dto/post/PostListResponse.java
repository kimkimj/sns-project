package com.example.finalproject.domain.dto.post;


import com.example.finalproject.domain.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostListResponse {
    private List list;
    private Pageable pages;
}
