package com.example.onlineshop.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import com.example.onlineshop.model.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.stereotype.Component;

public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;
    private final Long id;
    private final String email;
    private final String password;
    private final String firstName;
    @JsonIgnore
    private final List<GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String email, String password,String firstName,
                           List<GrantedAuthority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(UserEntity userEntity) {
        List<GrantedAuthority> authorities = List.of(
                new SimpleGrantedAuthority("ROLE_" + userEntity.getRole().name()));

        return new UserDetailsImpl(
                userEntity.getId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getFirstName(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public Long getId() {
        return this.id;
    }

    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}