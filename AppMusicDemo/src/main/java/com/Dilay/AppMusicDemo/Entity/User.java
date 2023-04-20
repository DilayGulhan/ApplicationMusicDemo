package com.Dilay.AppMusicDemo.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;

@Setter
@Getter
@Builder //build object in easy way
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table (name = "useer")
@ToString
public class User  implements UserDetails {

 @Id
 @GeneratedValue(strategy = GenerationType.SEQUENCE)
//@Column(name = "userId")
 private int id;
 @Column(nullable = false)
 private String name;
 @Column(nullable = false)
 private String surname;
 @Column(nullable = false)
 private String email;
 @Column(nullable = false)

 private String password;
 @NotNull
 @Enumerated(EnumType.STRING)
 private Role role;

//    @JoinTable(name = "Subscription" , joinColumns = @JoinColumn(name = "userId") , inverseJoinColumns = @JoinColumn(name = "subscriptionId" ))
//    @ManyToMany
//    private List<Subscription> subscriptions ;

 public User(String name, String surname, String email, String password) {
  this.name = name;
  this.surname = surname;
  this.email = email;
  this.password = password;
 }

 private String verificationCode;
 private ZonedDateTime verificationCodeExpiredDate;
 private ZonedDateTime recoveryCodeExpiredDate;


 private String recoveryCode;

 private boolean isVerified;


 @Override
 public Collection<? extends GrantedAuthority> getAuthorities() {
  return List.of(new SimpleGrantedAuthority(role.name()));
 }

 @Override
 public String getUsername() {
  return email;
 }

 @Override
 public boolean isAccountNonExpired() {
  return true;
 }

 @Override
 public boolean isAccountNonLocked() {
  return true;
 }

 @Override
 public boolean isCredentialsNonExpired() {
  return true;
 }

 @Override
 public boolean isEnabled() {
  return false;
 }
}

