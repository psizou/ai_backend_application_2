package com.jsonplaceholder.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "company")
public class Company {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Company name is required")
  @Size(max = 100, message = "Company name must not exceed 100 characters")
  private String name;

  @NotBlank(message = "Catch phrase is required")
  @Size(max = 200, message = "Catch phrase must not exceed 200 characters")
  private String catchPhrase;

  @NotBlank(message = "Business strategy is required")
  @Size(max = 200, message = "Business strategy must not exceed 200 characters")
  private String bs;
}
