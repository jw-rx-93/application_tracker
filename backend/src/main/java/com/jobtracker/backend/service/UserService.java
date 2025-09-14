package com.jobtracker.backend.service;

import com.jobtracker.backend.model.User;
import com.jobtracker.backend.repository.UserRepostory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepostory userRepository;
  private final PasswordEncoder passwordEncoder; 

  public User createUser(String name, String email, String rawPassword) {
    validatePassword(rawPassword);
    String encodedPassword = passwordEncoder.encode(rawPassword);

    User user = User.builder()
                  .username(name)
                  .email(email)
                  .password(encodedPassword)
                  .build();
    
    return userRepository.save(user); 
  }

  public boolean verifyPassword(User user, String rawPassword) {
    return passwordEncoder.matches(rawPassword, user.getPassword());
  }

  public User updatePassword(User user, String rawPassword) {
    validatePassword(rawPassword);
    String newPassword = passwordEncoder.encode(rawPassword);
    user.setPassword(newPassword);
    return userRepository.save(user);
  }

  // #Private 

  private void validatePassword(String rawPassword) {
    if (rawPassword.length() < 8) {
      throw new IllegalArgumentException("Password is too short. Must be between 8 and 100 long");
    }

    if (!rawPassword.matches(".*[A-Z].*")) {
      throw new IllegalArgumentException("Password must contain at least one uppercase letter");
    }

    if (!rawPassword.matches(".*[a-z].*")) {
      throw new IllegalArgumentException("Password must contain at least one lowercase letter");
    }

    if (!rawPassword.matches(".*\\d.*")) {
      throw new IllegalArgumentException("Password must contain at least one number");
    }

    if (!rawPassword.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*")) {
      throw new IllegalArgumentException("Password must contain at least one special character");
    }
  }
}
