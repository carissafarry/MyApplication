package com.example.myapplication.interfaces;

public interface ApiCallback<T> {
    void onSuccess(T response);
    void onFailure(String errorMessage);
}
