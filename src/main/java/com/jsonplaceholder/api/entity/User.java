package com.jsonplaceholder.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Name is required")
  @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
  @Column(nullable = false)
  private String name;

  @NotBlank(message = "Username is required")
  @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
  @Column(nullable = false, unique = true)
  private String username;

  @NotBlank(message = "Email is required")
  @Email(message = "Email should be valid")
  @Column(nullable = false, unique = true)
  private String email;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "address_id", referencedColumnName = "id")
  private Address address;

  @Size(max = 20, message = "Phone number must not exceed 20 characters")
  private String phone;

  @Size(max = 100, message = "Website must not exceed 100 characters")
  private String website;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "company_id", referencedColumnName = "id")
  private Company company;
}
