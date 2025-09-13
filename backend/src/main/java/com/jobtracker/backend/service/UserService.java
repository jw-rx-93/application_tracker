package com.jobtracker.backend.service;

import com.jobtracker.backend.model.User;
import com.jobtracker.backend.repository.UserRepostory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

public class UserService {
  private final UserRepostory userRepository;
  private final PasswordEncoder passwordEncoder; 

  public User createUser(String name, String email, String rawPassword) {
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
    String newPassword = passwordEncoder.encode(rawPassword);
    user.setPassword(newPassword);
    return userRepository.save(user);
  }
}
