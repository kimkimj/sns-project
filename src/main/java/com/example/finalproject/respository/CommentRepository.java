package com.example.finalproject.respository;

import com.example.finalproject.domain.entity.Comment;
import com.example.finalproject.domain.entity.Post;
import com.example.finalproject.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository<Comment, Long> {
//    Page<Comment> findAllByPost(Post post, Pageable pageable);

/*    @Query(value = "select c from Comment c join fetch c.post p join fetch p.user where c.post = :post",
    countQuery = "select count(c) from Comment c join fetch c.post p join fetch p.user where c.post = :post")
    Page<Comment> findAllByPost(@Param(value = "post") Post post, Pageable pageable);*/

    @EntityGraph(attributePaths = {"post", "user"})
    Page<Comment> findAllByPost(Post post, Pageable pageable);
}

