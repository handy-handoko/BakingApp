package com.digitalnusantarastudio.bakingapp.Utils;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by luqman on 29/08/17.
 */

public class NetworkUtils {
    final static private String URL = "http://go.udacity.com/android-baking-app-json";


    public static URL buildUrl(){
        Uri.Builder builder = Uri.parse(URL).buildUpon();

        URL url = null;
        try {
            url = new URL(builder.build().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String sendPostRequest(URL url, RequestBody requestBody) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder().build();
        Request request = new Request.Builder().url(url)
                .post(requestBody).build();

        String result = "";
        Response response = client.newCall(request).execute();
        if(response.isSuccessful()) {
            result = response.body().string();
            response.close();
        }
        return result;
    }

    public static String getResponse(URL url, RequestBody requestBody) throws IOException{
        String response = NetworkUtils.sendPostRequest(url, requestBody);
        return response;
    }

    public static JSONObject getJsonObjectResponse(URL url, RequestBody requestBody) throws IOException, JSONException {
        return new JSONObject(getResponse(url, requestBody));
    }

    public static JSONArray getJsonArrayResponse(URL url, RequestBody requestBody) throws IOException, JSONException {
        return new JSONArray(getResponse(url, requestBody));
    }
}
