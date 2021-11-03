package com.example.covid.presenters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.method.Touch;
import android.widget.Toast;

import com.example.covid.ServiceHTTP_POST;
import com.example.covid.ServiceHTTP_PUT;
import com.example.covid.interfaces.IReservas;
import com.example.covid.interfaces.IUserLogin;
import com.example.covid.models.ReservaModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReservasPresenter implements IReservas.Presenter {
    private IReservas.View view;
    private ReservaModel model;
    private static final String URI_LOGIN_USER = "http://so-unlam.net.ar/api/api/login",
            URI_REGISTER_EVENT = "http://so-unlam.net.ar/api/api/event",
            URI_REFRESH = "http://so-unlam.net.ar/api/api/refresh",
            JSON_ENV = "env",
            JSON_TYPE_EVENTS = "type_events",
            JSON_DESC = "description",
            JSON_EVENTO = "evento",
            JSON_URI = "uri",
            JSON_DATA = "datosJson",
            JSON_TOKEN_EVENTO = "tokenEvento",
            JSON_TOKEN_REFRESH = "token_refresh",
            JSON_REGISTRAR_EVENTO = "RegistrarEvento",
            JSON_URL = "url",
            JSON_TOKEN_PUT = "token_refresh_put";


    public ReservasPresenter(IReservas.View view) {
        this.view = view;
        model = new ReservaModel(this);
    }

    @Override
    public void reservar(Date fechaSeleccionada, String cliente, Context c) {

        model.makeReservation(cliente, fechaSeleccionada, c);
    }

    @Override
    public void postearReserva(String env, String event, String desc, String token, String token_refresh) {


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
    public void actulizarToken(String token_refresh) {


        Intent i = new Intent((Context) view, ServiceHTTP_PUT.class);
        i.putExtra(JSON_URL, URI_REFRESH);
        i.putExtra(JSON_TOKEN_PUT, token_refresh);
        ((Context) view).startService(i);


    }

    @Override
    public void RegistrarCantidadShakes(Context c, String user) {
        model.RegistrarCantidadShakes(c, user);
    }


}
