package com.jsonplaceholder.api.service;

import com.jsonplaceholder.api.entity.User;
import com.jsonplaceholder.api.exception.ResourceNotFoundException;
import com.jsonplaceholder.api.repository.UserRepository;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Transactional
  public User createUser(User user) {
    if (userRepository.existsByUsername(user.getUsername())) {
      throw new IllegalArgumentException("Username is already taken");
    }

    if (userRepository.existsByEmail(user.getEmail())) {
      throw new IllegalArgumentException("Email is already in use");
    }

    return userRepository.save(user);
  }

  public User getUserById(Long id) {
    return userRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
  }

  public User getUserByUsername(String username) {
    return userRepository
        .findByUsername(username)
        .orElseThrow(
            () -> new ResourceNotFoundException("User not found with username: " + username));
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }

  @Transactional
  public User updateUser(Long id, User userDetails) {
    User user = getUserById(id);

    // Update basic fields
    user.setName(userDetails.getName());
    user.setEmail(userDetails.getEmail());
    user.setPhone(userDetails.getPhone());
    user.setWebsite(userDetails.getWebsite());

    // Update address if provided
    if (userDetails.getAddress() != null) {
      user.setAddress(userDetails.getAddress());
    }

    // Update company if provided
    if (userDetails.getCompany() != null) {
      user.setCompany(userDetails.getCompany());
    }

    return userRepository.save(user);
  }

  @Transactional
  public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
      throw new ResourceNotFoundException("User not found with id: " + id);
    }
    userRepository.deleteById(id);
  }
}
