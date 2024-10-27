package utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static Retrofit retrofit = null;

    private static final String API_URL = "http://192.168.100.70:8000/";

    public static Retrofit getClient(Context context) {
        if (retrofit == null) {

            // Add token api to request Header using Interceptor by OkHttp
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException, IOException {
                            Request originalRequest = chain.request();

                            // Retrieve the token from SharedPreferences
                            SharedPreferences sharedPreferences = context.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                            String token = sharedPreferences.getString("api_token", null);

                            // Add the token to the request header if it exists
                            if (token != null) {
                                Request.Builder requestBuilder = originalRequest.newBuilder()
                                        .header("Authorization", "Bearer " + token);
                                Request request = requestBuilder.build();
                                return chain.proceed(request);
                            }

                            return chain.proceed(originalRequest);
                        }
                    })
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(API_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
