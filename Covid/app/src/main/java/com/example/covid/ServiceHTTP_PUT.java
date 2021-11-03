package com.example.covid;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ServiceHTTP_PUT extends IntentService {


    private Exception mExeption = null;
    private static final String     URL_KEY ="url",
                                    TOKEN_REFRESH_PUT = "token_refresh_put",
                                    RESPUESTA_PUT="com.example.intentservice.intent.action.RESPUESTA_PUT",
                                    ETIQUETA_REFRESH = "refresh";





    public ServiceHTTP_PUT(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String url = intent.getExtras().getString(URL_KEY);
        String token_refresh = intent.getExtras().getString( TOKEN_REFRESH_PUT);
        ejercutarPUT(url, token_refresh);

    }

    private void ejercutarPUT(String url, String token_refresh) {
        String result = PUT(url, token_refresh);

        if (result == null) {

            return;
        }

        if (result == "NO OK") {

            return;
        }

        Intent i = new Intent(RESPUESTA_PUT);
        i.putExtra(ETIQUETA_REFRESH, result);
        sendBroadcast(i);
    }

    private String PUT(String url, String token_refresh) {
        HttpURLConnection urlConnection = null;
        String result = "";

        try {
            URL mURL = new URL(url);

            urlConnection = (HttpURLConnection) mURL.openConnection();
            urlConnection.setRequestProperty("Content-Type", "aplication/json; charset=UTF-8");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token_refresh);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestMethod("PUT");
            urlConnection.connect();

            int responseCode = urlConnection.getResponseCode();
            if ((responseCode == HttpURLConnection.HTTP_OK) || (responseCode == HttpURLConnection.HTTP_CREATED)) {
                InputStreamReader inputStream = new InputStreamReader(urlConnection.getInputStream());
                result = convertInputStreamToString(inputStream).toString();
            } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {

                InputStreamReader inputStream = new InputStreamReader(urlConnection.getErrorStream());
                result = convertInputStreamToString(inputStream).toString();
            } else {
                result = "NO OK";
            }
            mExeption = null;
            urlConnection.disconnect();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            mExeption = e;
            return null;
        }


    }

    private Object convertInputStreamToString(InputStreamReader inputStream) throws IOException {
        BufferedReader br = new BufferedReader(inputStream);
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            result.append(line + "\n");

        }
        br.close();
        return result;

    }

}
