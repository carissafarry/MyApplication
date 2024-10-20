package utils;

import okhttp3.Response;

public interface ApiCallback {
    void onSuccess(Response response);
    void onFailure(String errorMessage);
}
