package com.example.task1apitransactions.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    private String token = null ;

    public String getToken() {
        return token != null ? token : "";
    }
}