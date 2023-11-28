package com.knowbidash.knowbidash.security.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.knowbidash.knowbidash.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DetailUserData implements UserDetails {
    private static final long SerialVersionUID = 1L;

    private Long id;
    private String user;
    private String aliasname;
    private String email;
    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public DetailUserData(Long id, String user, String aliasname, String email, String password, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.user = user;
        this.aliasname = aliasname;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static DetailUserData build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new DetailUserData(
                user.getId(),
                user.getUserName(),
                user.getAliasName(),
                user.getEmail(),
                user.getPassWord(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return authorities;
    }

    public Long getUserId() {
        return id;
    }

    public String getFullUserName() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return aliasname;
    }

    @Override
    public String getPassword() {
        return password;
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DetailUserData that = (DetailUserData) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
