package com.jobtracker.backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data // most of the getter, setter, and etc 
public class CreateUserDto {
  @NotBlank(message = "Username cannot be blank")
  @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
  @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "Username can only contain letters, numbers, and  underscores")
  private String username;

  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Email must be valid")
  private String Email;

  @NotBlank(message = "Password is required")
  @Size(min = 8, message = "Password must be at least 8 characters")
  @Pattern(
    regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]+$",
    message = "Password must contain at least one uppercase, lowercase, digit, and special character")
  private String password;
}
