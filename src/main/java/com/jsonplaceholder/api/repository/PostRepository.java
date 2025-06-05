package com.jsonplaceholder.api.repository;

import com.jsonplaceholder.api.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
  Iterable<Post> findByUserId(Long userId);
}
