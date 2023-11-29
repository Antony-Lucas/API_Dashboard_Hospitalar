package com.knowbidash.knowbidash.security.payload.response;

import java.util.List;

public class UserInfoResponse {

    private Long id;
    private String userName;
    public String aliasName;
    private String email;
    private List<String> roles;

    public UserInfoResponse(Long id, String userName, String aliasName,String email, List<String> roles) {
        this.id = id;
        this.userName = userName;
        this.aliasName = aliasName;
        this.email = email;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getAliasName() {
        return aliasName;
    }

    public void setAliasName(String aliasName) {
        this.aliasName = aliasName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }
}
