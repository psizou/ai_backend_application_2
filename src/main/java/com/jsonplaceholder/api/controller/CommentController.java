package com.jsonplaceholder.api.controller;

import com.jsonplaceholder.api.entity.Comment;
import com.jsonplaceholder.api.service.CommentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

  private final CommentService commentService;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  @GetMapping
  public ResponseEntity<Iterable<Comment>> getAllComments() {
    return ResponseEntity.ok(commentService.getAllComments());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Comment> getCommentById(@PathVariable Long id) {
    return ResponseEntity.ok(commentService.getCommentById(id));
  }

  @GetMapping("/post/{postId}")
  public ResponseEntity<Iterable<Comment>> getCommentsByPostId(@PathVariable Long postId) {
    return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
  }

  @PostMapping("/post/{postId}")
  public ResponseEntity<Comment> createComment(
      @RequestBody Comment comment, @PathVariable Long postId) {
    return ResponseEntity.ok(commentService.createComment(comment, postId));
  }

  @PutMapping("/{id}")
  public ResponseEntity<Comment> updateComment(
      @PathVariable Long id, @RequestBody Comment commentDetails) {
    return ResponseEntity.ok(commentService.updateComment(id, commentDetails));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteComment(@PathVariable Long id) {
    commentService.deleteComment(id);
    return ResponseEntity.ok().build();
  }
}
