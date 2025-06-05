package com.jsonplaceholder.api.repository;

import com.jsonplaceholder.api.entity.Auth;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthRepository extends JpaRepository<Auth, Long> {
  Optional<Auth> findByEmail(String email);

  Optional<Auth> findByName(String name);
}
