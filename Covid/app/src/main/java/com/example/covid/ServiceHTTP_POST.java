package com.example.covid;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ServiceHTTP_POST extends IntentService {

    private HttpURLConnection httpConnection;

    private URL mUrl;
    String token;
    String token_refresh;
    Integer code = 0;
    private Exception mExeption = null;

    public ServiceHTTP_POST() {
        super("ServicesHttp GET");
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.i("LOGUEO SERVICE", "onCreate()");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try {

            String evento = intent.getExtras().getString("evento");
            String uri = intent.getExtras().getString("uri");
            JSONObject datosJson = new JSONObject(intent.getExtras().getString("datosJson"));
            if (evento.equals("RegistrarEvento")) {
                token = intent.getExtras().getString("tokenEvento");
                token_refresh = intent.getExtras().getString("token_refresh");
            }


            ejectuarPost(uri, datosJson, evento);


        } catch (Exception e) {
            //e.printStackTrace();
            Log.i("LOGUEO SERVICE", "Error" + e.toString());
        }
    }

    protected void ejectuarPost(String uri, JSONObject datosJson, String evento) {

        if (evento.equals("log")) {

            String result = POST(uri, datosJson, evento);
            if (result == null) {
                Log.i("LOGUEO SERVICE", "Error en POST:\n" + mExeption.toString());
                return;
            }

            if (result == "NO OK") {
                Log.i("LOGUEO SERVICE", "se recibio response NO OK");
                return;
            }


            Intent i = new Intent("com.example.intentservice.intent.action.RESPUESTA_OPERACION");
            i.putExtra("datosJson", result);
            sendBroadcast(i);

        } else if (evento.equals("RegistrarEvento")) {

            String result = POST_Evento(uri, datosJson, evento);
            if (result == null) {
                Log.i("LOGUEO SERVICE", "Error en POST:\n" + mExeption.toString());
                return;
            }

            if (result == "NO OK") {
                Log.i("LOGUEO SERVICE", "se recibio response NO OK");
                return;
            }


            Intent i = new Intent("com.example.intentservice.intent.action.POST_EVENTO");
            i.putExtra("datosJson", result);
            if (code == HttpURLConnection.HTTP_CLIENT_TIMEOUT) {
                i.putExtra("timeout", code);
            }
            sendBroadcast(i);
        }

    }

    private String POST_Evento(String uri, JSONObject datosJson, String evento) {
        HttpURLConnection urlConnection = null;
        String result = "";

        try {
            URL mURL = new URL(uri);

            urlConnection = (HttpURLConnection) mURL.openConnection();
            urlConnection.setRequestProperty("Content-Type", "aplication/json; charset=UTF-8");
            urlConnection.setRequestProperty("Authorization", "Bearer " + token);
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestMethod("POST");

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.write(datosJson.toString().getBytes("UTF-8"));
            Log.i("LOGUEO SEVICE", "Se va a enviar  al servidor" + datosJson.toString());

            wr.flush();//si no limpio el buffer la conexion puede queadr colgada

            urlConnection.connect();

            Integer responseCode = urlConnection.getResponseCode();
            if ((responseCode == HttpURLConnection.HTTP_OK) || (responseCode == HttpURLConnection.HTTP_CREATED)) {
                InputStreamReader inputStream = new InputStreamReader(urlConnection.getInputStream());
                result = convertInputStreamToString(inputStream).toString();
            } else if (responseCode == HttpURLConnection.HTTP_BAD_REQUEST) {

                InputStreamReader inputStream = new InputStreamReader(urlConnection.getErrorStream());
                result = convertInputStreamToString(inputStream).toString();
            } else if (responseCode == HttpURLConnection.HTTP_CLIENT_TIMEOUT) {
                InputStreamReader inputStream = new InputStreamReader(urlConnection.getErrorStream());
                result = convertInputStreamToString(inputStream).toString();
                code = responseCode;
            } else {
                result = "NO OK";
            }
            mExeption = null;
            wr.close();
            urlConnection.disconnect();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            mExeption = e;
            return null;
        }

    }

    private String POST(String uri, JSONObject datosJson, String evento) {

        HttpURLConnection urlConnection = null;
        String result = "";
        try {
            URL mURL = new URL(uri);

            urlConnection = (HttpURLConnection) mURL.openConnection();
            urlConnection.setRequestProperty("Content-Type", "aplication/json; charset=UTF-8");
            urlConnection.setDoOutput(true);
            urlConnection.setDoInput(true);
            urlConnection.setConnectTimeout(5000);
            urlConnection.setRequestMethod("POST");

            DataOutputStream wr = new DataOutputStream(urlConnection.getOutputStream());
            wr.write(datosJson.toString().getBytes("UTF-8"));
            Log.i("LOGUEO SEVICE", "Se va a enviar  al servidor" + datosJson.toString());

            wr.flush();//si no limpio el buffer la conexion puede queadr colgada

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
            wr.close();
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
