package com.jsonplaceholder.api.controller;

import com.jsonplaceholder.api.dto.JwtResponse;
import com.jsonplaceholder.api.dto.LoginRequest;
import com.jsonplaceholder.api.dto.RegisterRequest;
import com.jsonplaceholder.api.entity.User;
import com.jsonplaceholder.api.service.AuthService;
import com.jsonplaceholder.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private final AuthService authService;
  private final UserService userService;

  public AuthController(AuthService authService, UserService userService) {
    this.authService = authService;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
    return ResponseEntity.ok(authService.authenticateUser(loginRequest));
  }

  @PostMapping("/register")
  public ResponseEntity<User> register(@Valid @RequestBody RegisterRequest registerRequest) {
    return ResponseEntity.ok(userService.createUser(registerRequest));
  }
}
