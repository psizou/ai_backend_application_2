package com.jsonplaceholder.api.controller;

import com.jsonplaceholder.api.entity.User;
import com.jsonplaceholder.api.service.UserService;
import jakarta.validation.Valid;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private static final Logger logger = LoggerFactory.getLogger(UserController.class);
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  public ResponseEntity<User> getCurrentUser(@AuthenticationPrincipal UserDetails userDetails) {
    logger.debug("Getting current user for username: {}", userDetails.getUsername());
    return ResponseEntity.ok(userService.getUserByUsername(userDetails.getUsername()));
  }

  @GetMapping
  public ResponseEntity<List<User>> getAllUsers() {
    logger.debug("Getting all users");
    return ResponseEntity.ok(userService.getAllUsers());
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable Long id) {
    logger.debug("Getting user by id: {}", id);
    return ResponseEntity.ok(userService.getUserById(id));
  }

  @PostMapping
  public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
    logger.debug("Creating new user: {}", user.getUsername());
    return ResponseEntity.ok(userService.createUser(user));
  }

  @PutMapping("/{id}")
  @PreAuthorize("authentication.principal.username == @userService.getUserById(#id).username")
  public ResponseEntity<User> updateUser(
      @PathVariable Long id, @Valid @RequestBody User userDetails) {
    logger.debug("Updating user with id: {}", id);
    return ResponseEntity.ok(userService.updateUser(id, userDetails));
  }

  @DeleteMapping("/{id}")
  @PreAuthorize("authentication.principal.username == @userService.getUserById(#id).username")
  public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
    logger.debug("Deleting user with id: {}", id);
    userService.deleteUser(id);
    return ResponseEntity.noContent().build();
  }
}
