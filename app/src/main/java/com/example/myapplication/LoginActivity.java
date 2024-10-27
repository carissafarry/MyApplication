package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.example.myapplication.databinding.ActivityLoginBinding;
import com.example.myapplication.interfaces.AuthApiService;
import com.example.myapplication.models.api.ApiResponse;
import com.example.myapplication.models.api.LoginRequest;
import com.example.myapplication.models.api.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.ApiClient;

public class LoginActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private EditText editTextUsername;
    private EditText editTextPassword;
    private Button buttonLogin;
    private ActivityLoginBinding binding;
    private AuthApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.usernameAuthEditText);
        editTextPassword = findViewById(R.id.passwordAuthEditText);
        buttonLogin = findViewById(R.id.loginAuthButton);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                login(username, password);
            }
        });
    }

    private void login(String username, String password) {
        apiService = ApiClient.getClient(this).create(AuthApiService.class);
        LoginRequest loginRequest = new LoginRequest(username, password);

        Call<ApiResponse<LoginResponse>> call = apiService.doAuthLogin(loginRequest);
        call.enqueue(new Callback<ApiResponse<LoginResponse>>() {
            @Override
            public void onResponse(Call<ApiResponse<LoginResponse>> call, Response<ApiResponse<LoginResponse>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse<LoginResponse> apiResponse = response.body();

                    if ("00".equals(apiResponse.getResponseCode())) {
                        String token = apiResponse.getResponseData().getToken();

                        // Store api token
                        SharedPreferences sharedPreferences = getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("api_token", token);
                        editor.apply();

                        // Redirect to MainActivity page
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("username", username);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Login failed: " + apiResponse.getResponseDesc(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Server error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<LoginResponse>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}