package com.example.myapplication.models.api;

import com.example.myapplication.models.User;

public class ApiResponse<T> {
    private String responseCode;
    private String responseDesc;
    private T responseData;

    // Getters and Setters
    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseDesc() {
        return responseDesc;
    }

    public void setResponseDesc(String responseDesc) {
        this.responseDesc = responseDesc;
    }

    public T getResponseData() {
        return responseData;
    }

    public void setResponseData(T responseData) {
        this.responseData = responseData;
    }

    public String getToken() {
        if (responseData instanceof LoginResponse) {
            return ((LoginResponse) responseData).getToken();
        }
        return null;
    }

    public User getUser() {
        if (responseData instanceof LoginResponse) {
            return ((LoginResponse) responseData).getUser();
        }
        return null;
    }
}
