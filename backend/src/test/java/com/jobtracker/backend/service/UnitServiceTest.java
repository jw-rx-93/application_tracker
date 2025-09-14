package com.jobtracker.backend.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.jobtracker.backend.repository.UserRepostory;
import com.jobtracker.backend.model.User;


@ExtendWith(MockitoExtension.class)
public class UnitServiceTest {
  
  @Mock 
  private UserRepostory userRepository;

  @Mock 
  private PasswordEncoder passwordEncoder; 

  @InjectMocks
  private UserService userService;

  private User testUser;

  @BeforeEach 
  void setUp() {
    testUser = User.builder()
      .username("test_user")
      .email("test@example.com")
      .password("encodedPassword123")
      .build();
  }

  @Test 
  @DisplayName("Should create user with encoded password")
  void testCreateUser(){
    String rawPassword = "MyPassword123!";
    String encodedPassword = "encodedPassword123";

    when(passwordEncoder.encode(rawPassword)).thenReturn(encodedPassword);
    when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
      User savedUser = invocation.getArgument(0);
      savedUser.setId(1L); 
      return savedUser; 
    });

    User createdUser = userService.createUser("test_user", "test@example.com", rawPassword);
    assertThat(createdUser).isNotNull();
    assertThat(createdUser.getEmail()).isEqualTo("test@example.com");
    assertThat(createdUser.getUsername()).isEqualTo("test_user");
    assertThat(createdUser.getPassword()).isEqualTo(encodedPassword);
    verify(passwordEncoder).encode(rawPassword);
    verify(userRepository).save(any(User.class));
  }

  @Test 
  @DisplayName("Should raise errors if password not valid")
  void testNotValidPassword(){

    String[] badPasswords = {"bad", "Bad", "badpassword", "Badpassword", "badpassword1", "badpassword$", 
        "Badpassword1", "Badpassword$"};

    for (String badPassword : badPasswords) {
      assertThrows(RuntimeException.class, () -> {
        userService.createUser("test_user", "test@example.com", badPassword);
      });
    }
  }

  @Test 
  @DisplayName("Should raise errors if username is not present")
  void testNoUsername() {
      assertThrows(RuntimeException.class, () -> {
      userService.createUser("", "test@example.com", "Password123!");
    });
  }
}
