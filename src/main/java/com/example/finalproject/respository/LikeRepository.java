package com.example.finalproject.respository;

import com.example.finalproject.domain.entity.Like;
import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {
    Long countByPost(Post post);
    Optional<Like> findByUserAndPost(User user, Post post);
}

