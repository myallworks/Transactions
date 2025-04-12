package com.example.task1apitransactions.api;

import retrofit2.Response;

public class ApiResponse {
    public static String getErrorMessage(Response<?> response) {
        try {
            if (response.errorBody() != null) {
                return response.errorBody().string();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response.message();
    }
}