package com.knowbidash.knowbidash.security.payload.response;

import java.util.List;

public class UserInfoResponse {

    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private Long id;
    private String userName;
    public String fullUserName;
    private String email;
    private List<String> roles;

    public UserInfoResponse(String accessToken, String refreshToken, Long id, String userName, String fullUserName, String email, List<String> roles) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.id = id;
        this.userName = userName;
        this.fullUserName = fullUserName;
        this.email = email;
        this.roles = roles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getfullUserName() {
        return fullUserName;
    }

    public void setfullUserName(String fullUserName) {
        this.fullUserName = fullUserName;
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

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
