package com.jobtracker.backend.model;

import jakarta.persistence.*;
import lombok.*;


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

  @Column(nullable = false, unique = true, length = 50)
  private String username;

  @Column(nullable = false, unique = true, length = 100)
  private String email;

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
