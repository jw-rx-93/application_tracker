package com.jobtracker.backend.model;

import jakarta.persistence.*;
import lombok.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder 
@EqualsAndHashCode(onlyExplicitlyIncluded = true) // makes it fall back to ID as comparison
@ToString(exclude = {"password"})
public class User extends BaseEntity {

  @NotBlank(message = "Username is required")
  @Size(min = 3, max = 50, message = "Username must be between 3 and 50 chars")
  @Column(nullable = false, unique = false, length = 50)
  private String username;

  @NotBlank(message = "Email cannot be blank")
  @Email(message = "Must be an email")
  @Column(nullable = false, unique = true, length = 100)
  private String email;
  
  @NotBlank(message = "Password cannot be blank")
  @Size(min = 8, max=100, message = "Password must be between 8 and 100")
  @Column(nullable = false)
  private String password;

  @Column(nullable = false)
  @Builder.Default
  private Boolean confirmed = false;

  // relationships 
  // @ManyToMany(fetch = FetchType.EAGER)
  // @JoinTable(
  //  name = "user_roles",
  //  joinColumns = @JoinColumn(name = "user_id"),
  //  inverseJoinColumns = @JoinColumn(name = "role_id")
  //)
}
