package com.jsonplaceholder.api.service;

import com.jsonplaceholder.api.entity.Comment;
import com.jsonplaceholder.api.entity.Post;
import com.jsonplaceholder.api.exception.ResourceNotFoundException;
import com.jsonplaceholder.api.repository.CommentRepository;
import com.jsonplaceholder.api.repository.PostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {

  private final CommentRepository commentRepository;
  private final PostRepository postRepository;

  public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
    this.commentRepository = commentRepository;
    this.postRepository = postRepository;
  }

  public Iterable<Comment> getAllComments() {
    return commentRepository.findAll();
  }

  public Comment getCommentById(Long id) {
    return commentRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Comment not found with id: " + id));
  }

  public Iterable<Comment> getCommentsByPostId(Long postId) {
    if (!postRepository.existsById(postId)) {
      throw new ResourceNotFoundException("Post not found with id: " + postId);
    }
    return commentRepository.findByPostId(postId);
  }

  @Transactional
  public Comment createComment(Comment comment, Long postId) {
    Post post =
        postRepository
            .findById(postId)
            .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + postId));
    comment.setPost(post);
    return commentRepository.save(comment);
  }

  @Transactional
  public Comment updateComment(Long id, Comment commentDetails) {
    Comment comment = getCommentById(id);
    comment.setName(commentDetails.getName());
    comment.setEmail(commentDetails.getEmail());
    comment.setBody(commentDetails.getBody());
    return commentRepository.save(comment);
  }

  @Transactional
  public void deleteComment(Long id) {
    if (!commentRepository.existsById(id)) {
      throw new ResourceNotFoundException("Comment not found with id: " + id);
    }
    commentRepository.deleteById(id);
  }
}
