package com.jsonplaceholder.api.service;

import com.jsonplaceholder.api.dto.JwtResponse;
import com.jsonplaceholder.api.dto.LoginRequest;
import com.jsonplaceholder.api.dto.RegisterRequest;
import com.jsonplaceholder.api.entity.Auth;
import com.jsonplaceholder.api.entity.User;
import com.jsonplaceholder.api.repository.AuthRepository;
import com.jsonplaceholder.api.repository.UserRepository;
import com.jsonplaceholder.api.security.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {
  private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

  private final UserRepository userRepository;
  private final AuthRepository authRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider tokenProvider;
  private final PasswordEncoder passwordEncoder;

  public AuthService(
      UserRepository userRepository,
      AuthRepository authRepository,
      AuthenticationManager authenticationManager,
      JwtTokenProvider tokenProvider,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.authRepository = authRepository;
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public User registerUser(RegisterRequest registerRequest) {
    logger.debug("Attempting to register user: {}", registerRequest.getUsername());

    if (userRepository.existsByUsername(registerRequest.getUsername())) {
      logger.warn(
          "Registration failed: Username {} is already taken", registerRequest.getUsername());
      throw new IllegalArgumentException("Username is already taken");
    }
    if (userRepository.existsByEmail(registerRequest.getEmail())) {
      logger.warn("Registration failed: Email {} is already in use", registerRequest.getEmail());
      throw new IllegalArgumentException("Email is already in use");
    }

    User user = new User();
    user.setName(registerRequest.getName());
    user.setUsername(registerRequest.getUsername());
    user.setEmail(registerRequest.getEmail());
    user = userRepository.save(user);
    logger.debug("User created with ID: {}", user.getId());

    Auth auth = new Auth();
    auth.setName(registerRequest.getUsername());
    auth.setEmail(registerRequest.getEmail());
    auth.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));
    auth.setUser(user);
    authRepository.save(auth);
    logger.debug("Auth record created for user: {}", user.getUsername());

    return user;
  }

  public JwtResponse authenticateUser(LoginRequest loginRequest) {
    logger.debug("Attempting to authenticate user: {}", loginRequest.getUsername());

    try {
      Authentication authentication =
          authenticationManager.authenticate(
              new UsernamePasswordAuthenticationToken(
                  loginRequest.getUsername(), loginRequest.getPassword()));

      logger.debug("Authentication successful for user: {}", loginRequest.getUsername());

      SecurityContextHolder.getContext().setAuthentication(authentication);
      String jwt = tokenProvider.generateToken(authentication);
      logger.debug("JWT token generated for user: {}", loginRequest.getUsername());

      UserDetails userDetails = (UserDetails) authentication.getPrincipal();
      User user =
          userRepository
              .findByUsername(userDetails.getUsername())
              .orElseThrow(
                  () -> {
                    logger.error(
                        "User not found after successful authentication: {}",
                        userDetails.getUsername());
                    return new RuntimeException("User not found after authentication");
                  });

      logger.debug("User details retrieved for: {}", user.getUsername());
      return new JwtResponse(jwt, user.getId(), user.getUsername(), user.getEmail());
    } catch (Exception e) {
      logger.error(
          "Authentication failed for user: {}. Error: {}",
          loginRequest.getUsername(),
          e.getMessage());
      throw e;
    }
  }
}
