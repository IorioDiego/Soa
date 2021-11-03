package com.example.covid.presenters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.covid.ServiceHTTP_POST;
import com.example.covid.interfaces.IPatronDesbloqueo;
import com.example.covid.interfaces.IUserLogin;
import com.example.covid.models.UserLoginModel;

import org.json.JSONException;
import org.json.JSONObject;

public class UserLoginPresenter implements IUserLogin.Presenter {

    private IUserLogin.View view;
    private IUserLogin.Model model;
    private static final String URI_LOGIN_USER = "http://so-unlam.net.ar/api/api/login",
            JSON_ENV = "env",
            JSON_TYPE_EVENTS = "type_events",
            JSON_DESC = "description",
            JSON_EVENTO = "evento",
            JSON_URI = "uri",
            JSON_DATA = "datosJson",
            JSON_TOKEN_EVENTO = "tokenEvento",
            JSON_TOKEN_REFRESH = "token_refresh",
            JSON_REGISTRAR_EVENTO = "RegistrarEvento",
            JSON_LOG = "log",
            URI_REGISTER_EVENT = "http://so-unlam.net.ar/api/api/event",
            JSON_EMAIL = "email",
            JSON_PASSWORD = "password";


    public UserLoginPresenter(IUserLogin.View view) {
        this.view = view;
        model = new UserLoginModel(this);
    }

    @Override
    public void loguearse(String email, String pssw) {

        JSONObject obj = new JSONObject();
        try {
            obj.put(JSON_EMAIL, email);
            obj.put(JSON_PASSWORD, pssw);
            Intent i = new Intent((Context) view, ServiceHTTP_POST.class);
            i.putExtra(JSON_EVENTO, JSON_LOG);
            i.putExtra(JSON_URI, URI_LOGIN_USER);
            i.putExtra(JSON_DATA, obj.toString());
            ((Context) view).startService(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void registrarEvento(String env, String event, String desc, String token, String token_refresh) {

        JSONObject obj = new JSONObject();
        try {

            obj.put(JSON_ENV, env);
            obj.put(JSON_TYPE_EVENTS, event);
            obj.put(JSON_DESC, desc);


            Intent i = new Intent((Context) view, ServiceHTTP_POST.class);
            i.putExtra(JSON_EVENTO, JSON_REGISTRAR_EVENTO);
            i.putExtra(JSON_URI, URI_REGISTER_EVENT);
            i.putExtra(JSON_DATA, obj.toString());
            i.putExtra(JSON_TOKEN_EVENTO, token);
            i.putExtra(JSON_TOKEN_REFRESH, token_refresh);

            ((Context) view).startService(i);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void registrarCantidadLogueos(Context c) {
        model.registrarCantidadLogueos(c);
    }

    @Override
    public String leerCantDeLogueos(Context c) {
        return model.leerCantDeLogueos(c);

    }


}
