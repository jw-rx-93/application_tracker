package com.jobtracker.backend.model;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.assertj.core.api.Assertions.assertThat;


class UserTest {
  @Test 
  @DisplayName("Should create user with builder pattern")
  void testUserBuilder() {
    User user = User.builder()
                .username("john_doe")
                .email("john@example.com")
                .password("12345678")
                .build();

    assertThat(user.getUsername()).isEqualTo("john_doe");
    assertThat(user.getEmail()).isEqualTo("john@example.com");
  }

  @Test
  @DisplayName("Should not be confirmed by default")
  void testUserConfirmDefault() {
    User user = User.builder()
        .username("john_doe")
        .email("john@example.com")
        .password("12345678")
        .build();
    
    assertThat(user.getConfirmed()).isFalse();
  }
}