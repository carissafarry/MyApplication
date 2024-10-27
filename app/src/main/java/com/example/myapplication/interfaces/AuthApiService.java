package com.example.myapplication.interfaces;

import com.example.myapplication.models.api.ApiResponse;
import com.example.myapplication.models.api.LoginRequest;
import com.example.myapplication.models.api.LoginResponse;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthApiService {
    @POST("/auth/login")
    Call<ApiResponse<LoginResponse>> doAuthLogin(@Body LoginRequest request);
}
