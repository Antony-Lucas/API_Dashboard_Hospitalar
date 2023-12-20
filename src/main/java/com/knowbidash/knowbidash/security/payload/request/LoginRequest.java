package com.knowbidash.knowbidash.security.payload.request;

import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
    @NotBlank
    private String userName;
    @NotBlank
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
