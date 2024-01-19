package com.knowbidash.knowbidash.security.payload.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.Set;

public class SignUpRequest {

    private Long id;
    @NotBlank
    @Size(min = 3, max = 30)
    private String userName;

    @NotBlank
    @Size(max = 40)
    private String fullUserName;
    private String cargo;

    @NotBlank
    @Size(max = 50)
    @Email
    private  String email;

    private Set<String> role;

    @NotBlank
    @Size(min = 3, max = 40)
    private String passWord;

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

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Set<String> getRole() {
        return this.role;
    }

    public void setRole(Set<String> role) {
        this.role = role;
    }

}
