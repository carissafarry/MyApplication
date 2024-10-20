package utils;

import android.content.Context;
import android.widget.Toast;

import org.json.JSONArray;
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
    public void postRequest(String url, JSONObject jsonData, Context context, ApiCallback<JSONArray> apiCallback) {
        RequestBody body = RequestBody.create(jsonData.toString(), JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        sendRequest(request, context, apiCallback);
    }

    // Generic method to send GET requests
    public void getRequest(String url, Context context, ApiCallback<JSONArray> apiCallback) {
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();

        sendRequest(request, context, apiCallback);
    }

    // Helper method to send the HTTP request and handle the callback
    private void sendRequest(Request request, Context context, ApiCallback<JSONArray> apiCallback) {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                handleError(context, "Request Failed: " + e.getMessage(), apiCallback, e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    handleSuccessResponse(response, apiCallback);
                } else {
                    handleError(context, "Request Failed: " + response.message(), apiCallback, response.message());
                }
            }
        });
    }

    // Generic method to handle success response
    public void handleSuccessResponse(Response response, ApiCallback<JSONArray> apiCallback) {
        try {
            String responseData = response.body().string();
            JSONObject jsonResponse = new JSONObject(responseData);
            String responseCode = jsonResponse.getString("responseCode");

            if (responseCode.equals("00")) {
                if (jsonResponse.has("responseData")) {
                    JSONArray responseDataArray = jsonResponse.getJSONArray("responseData");
                    apiCallback.onSuccess(responseDataArray);
                } else {
                    // If responseData does not exist, return a success message
                    apiCallback.onSuccess(new JSONArray()); // or handle as you see fit
                }
            } else {
                apiCallback.onFailure("Error: " + jsonResponse.getString("responseDesc"));
            }
        } catch (Exception e) {
            apiCallback.onFailure("Error: " + e.getMessage());
        }
    }

    // Helper method to handle errors and display a Toast
    private void handleError(Context context, String message, ApiCallback<JSONArray> apiCallback, String errorMessage) {
        // Make sure to display toast on the UI thread
        if (context != null) {
            ((android.app.Activity)context).runOnUiThread(() ->
                    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            );
        }
        apiCallback.onFailure(errorMessage);
    }
}
