package com.jsonplaceholder.api.service;

import com.jsonplaceholder.api.entity.Post;
import com.jsonplaceholder.api.entity.User;
import com.jsonplaceholder.api.exception.ResourceNotFoundException;
import com.jsonplaceholder.api.repository.PostRepository;
import com.jsonplaceholder.api.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

  private final PostRepository postRepository;
  private final UserRepository userRepository;

  public PostService(PostRepository postRepository, UserRepository userRepository) {
    this.postRepository = postRepository;
    this.userRepository = userRepository;
  }

  public Iterable<Post> getAllPosts() {
    return postRepository.findAll();
  }

  public Post getPostById(Long id) {
    return postRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Post not found with id: " + id));
  }

  public Iterable<Post> getPostsByUserId(Long userId) {
    if (!userRepository.existsById(userId)) {
      throw new ResourceNotFoundException("User not found with id: " + userId);
    }
    return postRepository.findByUserId(userId);
  }

  @Transactional
  public Post createPost(Post post, Long userId) {
    User user =
        userRepository
            .findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    post.setUser(user);
    return postRepository.save(post);
  }

  @Transactional
  public Post updatePost(Long id, Post postDetails) {
    Post post = getPostById(id);
    post.setTitle(postDetails.getTitle());
    post.setBody(postDetails.getBody());
    return postRepository.save(post);
  }

  @Transactional
  public void deletePost(Long id) {
    if (!postRepository.existsById(id)) {
      throw new ResourceNotFoundException("Post not found with id: " + id);
    }
    postRepository.deleteById(id);
  }
}
