package utils;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient {
    private static HttpClient instance;
    private final OkHttpClient client;
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    // Private constructor for singleton
    private HttpClient() {
        client = new OkHttpClient.Builder().build();
    }

    // Get the singleton instance of HttpClient
    public static synchronized HttpClient getInstance() {
        if (instance == null) {
            instance = new HttpClient();
        }
        return instance;
    }

    // Generic method to send POST requests
    public void postRequest(String url, JSONObject jsonData, Context context, ApiCallback apiCallback) {
        RequestBody body = RequestBody.create(jsonData.toString(), JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(context, "Request Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                apiCallback.onFailure(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    apiCallback.onSuccess(response);
                } else {
                    Toast.makeText(context, "Request Failed: " + response.message(), Toast.LENGTH_SHORT).show();
                    apiCallback.onFailure(response.message());
                }
            }
        });
    }
}
