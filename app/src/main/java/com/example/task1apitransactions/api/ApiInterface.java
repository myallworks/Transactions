package com.example.task1apitransactions.api;

import com.example.task1apitransactions.models.LoginReq;
import com.example.task1apitransactions.models.LoginResponse;
import com.example.task1apitransactions.models.Transaction;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface ApiInterface {

    @POST("login")
    Call<LoginResponse> login(
            @Body LoginReq loginReq
    );

    @GET("transactions")
    Call<List<Transaction>> getTransactions(
            @Header("Authorization") String token
    );
}