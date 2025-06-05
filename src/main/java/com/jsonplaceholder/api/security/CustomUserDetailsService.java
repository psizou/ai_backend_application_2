package com.jsonplaceholder.api.security;

import com.jsonplaceholder.api.entity.Auth;
import com.jsonplaceholder.api.repository.AuthRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
  private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

  private final AuthRepository authRepository;

  public CustomUserDetailsService(AuthRepository authRepository) {
    this.authRepository = authRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    logger.debug("Attempting to load user by username: {}", username);

    try {
      Auth auth =
          authRepository
              .findByName(username)
              .orElseThrow(
                  () -> {
                    logger.error("User not found with username: {}", username);
                    return new UsernameNotFoundException(
                        "User not found with username: " + username);
                  });

      logger.debug("Found auth record for user: {}", username);

      UserDetails userDetails =
          org.springframework.security.core.userdetails.User.withUsername(auth.getName())
              .password(auth.getPasswordHash())
              .authorities("USER")
              .accountExpired(false)
              .accountLocked(false)
              .credentialsExpired(false)
              .disabled(false)
              .build();

      logger.debug("Created UserDetails for user: {}", username);
      return userDetails;
    } catch (Exception e) {
      logger.error("Error loading user by username: {}. Error: {}", username, e.getMessage());
      throw e;
    }
  }
}
