package com.example.task1apitransactions.models;

public class LoginReq {
    String username;
    String password;

    public LoginReq(String uname, String pwd) {
        username = uname;
        password = pwd;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
