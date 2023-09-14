package com.example.finalproject.respository;

import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query(value = "select p from Post p left join fetch p.user where p.user = :user",
    countQuery = "select count(p) from Post p where p.user =:user")
    Page<Post> findAllByUser(@Param(value = "user") User user, Pageable pageable);
}
