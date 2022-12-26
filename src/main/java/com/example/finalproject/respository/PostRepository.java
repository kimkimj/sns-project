package com.example.finalproject.respository;

import com.example.finalproject.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long>  {

}
