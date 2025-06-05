package com.jsonplaceholder.api.repository;

import com.jsonplaceholder.api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
  boolean existsByUsername(String username);

  boolean existsByEmail(String email);

  User findByUsername(String username);
}
