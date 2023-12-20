package com.knowbidash.knowbidash.security.Data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.knowbidash.knowbidash.entities.postgres.user.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DetailUserData implements UserDetails {
    private static final long SerialVersionUID = 1L;

    private Long id;
    private String userName;
    private String fullUserName;
    private String email;
    @JsonIgnore
    private String passWord;
    private Collection<? extends GrantedAuthority> authorities;

    public DetailUserData(Long id, String userName, String fullUserName, String email, String passWord, Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.userName = userName;
        this.fullUserName = fullUserName;
        this.email = email;
        this.passWord = passWord;
        this.authorities = authorities;
    }

    public static DetailUserData build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new DetailUserData(
                user.getId(),
                user.getUserName(),
                user.getfullUserName(),
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
        return fullUserName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public String getPassword() {
        return passWord;
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
