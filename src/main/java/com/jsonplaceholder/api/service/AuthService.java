package com.jsonplaceholder.api.service;

import com.jsonplaceholder.api.dto.JwtResponse;
import com.jsonplaceholder.api.dto.LoginRequest;
import com.jsonplaceholder.api.entity.User;
import com.jsonplaceholder.api.repository.UserRepository;
import com.jsonplaceholder.api.security.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider tokenProvider;

  public AuthService(
      UserRepository userRepository,
      AuthenticationManager authenticationManager,
      JwtTokenProvider tokenProvider) {
    this.userRepository = userRepository;
    this.authenticationManager = authenticationManager;
    this.tokenProvider = tokenProvider;
  }

  public JwtResponse authenticateUser(LoginRequest loginRequest) {
    Authentication authentication =
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = tokenProvider.generateToken(authentication);

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    User user = userRepository.findByUsername(userDetails.getUsername());

    return new JwtResponse(jwt, user.getId(), user.getUsername(), user.getEmail());
  }
}
