package com.jsonplaceholder.api.repository;

import com.jsonplaceholder.api.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
  Iterable<Comment> findByPostId(Long postId);
}
