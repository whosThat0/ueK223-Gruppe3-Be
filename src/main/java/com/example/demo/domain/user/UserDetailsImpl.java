package com.example.demo.domain.user;

import java.util.Collection;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public record UserDetailsImpl(User user) implements UserDetails {

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return user.getRoles().stream()
            .flatMap(role -> {
              Stream<GrantedAuthority> roleStream =
                      Stream.of(new SimpleGrantedAuthority("ROLE_" + role.getName()));

              Stream<GrantedAuthority> authStream = role.getAuthorities().stream()
                      .map(auth -> new SimpleGrantedAuthority(auth.getName()));

              return Stream.concat(roleStream, authStream);
            })
            .toList();
  }

  @Override
  public String getPassword() {
    return user.getPassword();
  }

  @Override
  public String getUsername() {
    return user.getEmail();
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
    return true;
  }
}
