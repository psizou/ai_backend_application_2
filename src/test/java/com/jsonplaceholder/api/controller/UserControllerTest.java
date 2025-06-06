package com.jsonplaceholder.api.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jsonplaceholder.api.entity.User;
import com.jsonplaceholder.api.security.CustomUserDetailsService;
import com.jsonplaceholder.api.security.JwtTokenProvider; // Import this
import com.jsonplaceholder.api.security.SecurityConfig;
import com.jsonplaceholder.api.service.UserService;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
@Import(SecurityConfig.class)
class UserControllerTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  @MockBean private UserService userService;

  // Mock all dependencies of SecurityConfig to isolate the web layer
  @MockBean private CustomUserDetailsService customUserDetailsService;
  @MockBean private JwtTokenProvider jwtTokenProvider; // The missing mock bean

  @Test
  @WithMockUser
  void getAllUsers_ShouldReturnUsers() throws Exception {
    // Given
    User user1 = new User();
    user1.setId(1L);
    user1.setName("John Doe");
    user1.setEmail("john@example.com");
    user1.setUsername("johndoe");

    User user2 = new User();
    user2.setId(2L);
    user2.setName("Jane Doe");
    user2.setEmail("jane@example.com");
    user2.setUsername("janedoe");

    when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

    // When & Then
    mockMvc
        .perform(
            get("/api/users").accept(MediaType.APPLICATION_JSON)) // Added accept header for clarity
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$[0].id").value(1))
        .andExpect(jsonPath("$[0].name").value("John Doe"))
        .andExpect(jsonPath("$[1].id").value(2))
        .andExpect(jsonPath("$[1].name").value("Jane Doe"))
        .andDo(print());
  }

  @Test
  @WithMockUser
  void getUserById_ShouldReturnUser() throws Exception {
    // Given
    User user = new User();
    user.setId(1L);
    user.setName("John Doe");
    user.setEmail("john@example.com");
    user.setUsername("johndoe");

    when(userService.getUserById(1L)).thenReturn(user);

    // When & Then
    mockMvc
        .perform(
            get("/api/users/1")
                .accept(MediaType.APPLICATION_JSON)) // Added accept header for clarity
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.id").value(1))
        .andExpect(jsonPath("$.name").value("John Doe"))
        .andExpect(jsonPath("$.email").value("john@example.com"))
        .andExpect(jsonPath("$.username").value("johndoe"))
        .andDo(print());
  }
}
