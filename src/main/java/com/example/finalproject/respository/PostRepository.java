package com.example.finalproject.respository;

import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long>  {
    Page<Post> findAllByUser(User user, Pageable pageable);
}

