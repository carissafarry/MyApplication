package com.example.myapplication.interfaces;

import com.example.myapplication.models.Absensi;
import com.example.myapplication.models.api.AbsensiRequest;
import com.example.myapplication.models.api.ApiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AbsensiApiService {
    @POST("/absensi/getAll")
    Call<ApiResponse<List<Absensi>>> getAbsensiAll(@Body AbsensiRequest request);

    @POST("/absensi/getAbsensi")
    Call<ApiResponse<Absensi>> getAbsensiDetail(@Body AbsensiRequest request);

    @POST("/absensi/create")
    Call<ApiResponse<Void>> createAbsensi(@Body AbsensiRequest request);
}
