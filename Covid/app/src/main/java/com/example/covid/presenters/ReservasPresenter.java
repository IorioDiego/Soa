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
    private static final String URI_LOGIN_USER = "http://so-unlam.net.ar/api/api/login";
    private static final String URI_REGISTER_EVENT = "http://so-unlam.net.ar/api/api/event";
    private static final String URI_REFRESH = "http://so-unlam.net.ar/api/api/refresh";


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

            obj.put("env", env);
            obj.put("type_events", event);
            obj.put("description", desc);


            Intent i = new Intent((Context) view, ServiceHTTP_POST.class);
            i.putExtra("evento", "RegistrarEvento");
            i.putExtra("uri", URI_REGISTER_EVENT);
            i.putExtra("datosJson", obj.toString());
            i.putExtra("tokenEvento", token);
            i.putExtra("token_refresh", token_refresh);


            ((Context) view).startService(i);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void actulizarToken(String token_refresh) {


        Intent i = new Intent((Context) view, ServiceHTTP_PUT.class);
        i.putExtra("url", URI_REFRESH);
        i.putExtra("token_refresh_put", token_refresh);
        ((Context) view).startService(i);


    }

    @Override
    public void RegistrarCantidadShakes(Context c, String user) {
        model.RegistrarCantidadShakes(c, user);
    }


}
