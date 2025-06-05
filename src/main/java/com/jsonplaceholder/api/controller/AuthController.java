package com.jsonplaceholder.api.controller;

import com.jsonplaceholder.api.dto.JwtResponse;
import com.jsonplaceholder.api.dto.LoginRequest;
import com.jsonplaceholder.api.dto.RegisterRequest;
import com.jsonplaceholder.api.entity.User;
import com.jsonplaceholder.api.service.AuthService;
import com.jsonplaceholder.api.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

  private final AuthService authService;
  private final UserService userService;

  public AuthController(AuthService authService, UserService userService) {
    this.authService = authService;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    logger.debug("Received login request for user: {}", loginRequest.getUsername());
    try {
      JwtResponse response = authService.authenticateUser(loginRequest);
      logger.debug("Login successful for user: {}", loginRequest.getUsername());
      return ResponseEntity.ok(response);
    } catch (Exception e) {
      logger.error(
          "Login failed for user: {}. Error: {}", loginRequest.getUsername(), e.getMessage());
      throw e;
    }
  }

  @PostMapping("/register")
  public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest registerRequest) {
    logger.debug("Received registration request for user: {}", registerRequest.getUsername());
    try {
      User user = authService.registerUser(registerRequest);
      logger.debug("Registration successful for user: {}", registerRequest.getUsername());
      return ResponseEntity.ok(user);
    } catch (Exception e) {
      logger.error(
          "Registration failed for user: {}. Error: {}",
          registerRequest.getUsername(),
          e.getMessage());
      throw e;
    }
  }
}
