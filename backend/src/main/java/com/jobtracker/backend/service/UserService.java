package com.jobtracker.backend.service;

import com.jobtracker.backend.dto.request.CreateUserDto;
import com.jobtracker.backend.model.User;
import com.jobtracker.backend.repository.UserRepostory;
import lombok.RequiredArgsConstructor;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.jobtracker.backend.exception.UserExceptions.*;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepostory userRepository;
  private final PasswordEncoder passwordEncoder; 

  public User createUser(CreateUserDto userDto) {
    validateUserDto(userDto);

    User user = User.builder()
                  .username(userDto.getUsername())
                  .email(userDto.getEmail())
                  .password(passwordEncoder.encode(userDto.getPassword()))
                  .build();
    
    return userRepository.save(user); 
  }

  public User updatePassword(User user, String rawPassword) {
    validatePassword(rawPassword);
    String newPassword = passwordEncoder.encode(rawPassword);
    user.setPassword(newPassword);
    return userRepository.save(user);
  }

  // #Private 

  private void validateUserDto(CreateUserDto dto) {
    if (dto.getUsername() == null || dto.getUsername().isBlank()) {
      throw new InvalidUserDataException("Username cannot be blank");
    }

    if (dto.getUsername().length() < User.minUsernameLength) {
      throw new InvalidUserDataException("Username is too short");
    }

    if (dto.getUsername().length() > User.maxStrLength) {
      throw new InvalidUserDataException("Username is too long");
    }

    if (dto.getEmail() == null || dto.getEmail().isBlank()) {
      throw new InvalidUserDataException("Email cannot be blank");
    }

    if (!EmailValidator.getInstance().isValid(dto.getEmail())) {
      throw new InvalidUserDataException("Email is not valid");
    }

    if (dto.getPassword() == null || dto.getPassword().isBlank()) {
      throw new InvalidUserDataException("Password cannot be blank.");
    }

    validatePassword(dto.getPassword());
  }

  private void validatePassword(String rawPassword) {
    if (rawPassword.length() < User.minPasswordLength) {
      throw new IllegalArgumentException("Password is too short. Must be between 8 and 100 chars long");
    }

    if (rawPassword.length() > User.maxStrLength) {
      throw new IllegalArgumentException("Password is too long. Must be between 8 and 100 chars long");
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
