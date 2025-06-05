package com.jsonplaceholder.api.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "address")
public class Address {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotBlank(message = "Street is required")
  @Size(max = 100, message = "Street must not exceed 100 characters")
  private String street;

  @NotBlank(message = "Suite is required")
  @Size(max = 50, message = "Suite must not exceed 50 characters")
  private String suite;

  @NotBlank(message = "City is required")
  @Size(max = 50, message = "City must not exceed 50 characters")
  private String city;

  @NotBlank(message = "Zipcode is required")
  @Size(max = 20, message = "Zipcode must not exceed 20 characters")
  private String zipcode;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "geo_id", referencedColumnName = "id")
  private Geo geo;
}
