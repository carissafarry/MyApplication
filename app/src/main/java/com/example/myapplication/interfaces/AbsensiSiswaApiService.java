package com.example.myapplication.interfaces;

import com.example.myapplication.models.api.AbsensiRequest;
import com.example.myapplication.models.api.ApiResponse;
import com.example.myapplication.models.api.DataAbsensiResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AbsensiSiswaApiService {
    @POST("/dataabsensi/getAll")
    Call<ApiResponse<List<DataAbsensiResponse>>> getDataAbsensiAll(@Body AbsensiRequest request);

    @POST("/dataabsensi/doAbsensi")
    Call<ApiResponse<Void>> doAbsensi(@Body AbsensiRequest request);
}
