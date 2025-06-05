package com.jsonplaceholder.api.controller;

import com.jsonplaceholder.api.entity.Post;
import com.jsonplaceholder.api.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
public class PostController {

  private final PostService postService;

  public PostController(PostService postService) {
    this.postService = postService;
  }

  @GetMapping
  public ResponseEntity<Iterable<Post>> getAllPosts() {
    return ResponseEntity.ok(postService.getAllPosts());
  }

  @GetMapping("/{id}")
  public ResponseEntity<Post> getPostById(@PathVariable Long id) {
    return ResponseEntity.ok(postService.getPostById(id));
  }

  @GetMapping("/user/{userId}")
  public ResponseEntity<Iterable<Post>> getPostsByUserId(@PathVariable Long userId) {
    return ResponseEntity.ok(postService.getPostsByUserId(userId));
  }

  @PostMapping("/user/{userId}")
  @PreAuthorize("authentication.principal.username == @userService.getUserById(#userId).username")
  public ResponseEntity<Post> createPost(@RequestBody Post post, @PathVariable Long userId) {
    return ResponseEntity.ok(postService.createPost(post, userId));
  }

  @PutMapping("/{id}")
  @PreAuthorize("authentication.principal.username == @postService.getPostById(#id).user.username")
  public ResponseEntity<Post> updatePost(@PathVariable Long id, @RequestBody Post postDetails) {
    return ResponseEntity.ok(postService.updatePost(id, postDetails));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("authentication.principal.username == @postService.getPostById(#id).user.username")
  public ResponseEntity<?> deletePost(@PathVariable Long id) {
    postService.deletePost(id);
    return ResponseEntity.ok().build();
  }
}
