package com.example.task1apitransactions.models;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    private final String token = "";

    public String getToken() {
        return token != null ? token : "";
    }
}