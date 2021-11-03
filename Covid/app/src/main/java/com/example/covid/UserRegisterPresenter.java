package com.example.covid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class UserRegisterPresenter implements IUserRegister.Presenter {

    private IUserRegister.View view;
    private IUserRegister.Model model;
    private static final String URI_REGISTER_USER = "http://so-unlam.net.ar/api/api/register";
    private static final String URI_REGISTER_EVENT = "http://so-unlam.net.ar/api/api/event";
    // public IntentFilter filtro;
    //  private ReceptorOperacion receiverReg = new ReceptorOperacion();


    private static final String ENV = "env", EVENTO_RESERVA = "Evento_Reserva",
            NAME = "name",
            LAST_NAME = "lastname",
            DNI = "dni",
            EMAIL = "email",
            PSW = "password",
            COM = "commission",
            GROUP = "group",
            EVENT = "evento",
            LOG_EVENT = "log",
            URI_KEY = "uri",
            DATOS_JSON_KEY = "datosJson",
            ERROR_CONEXION = "Error de conexion a la red";


    public UserRegisterPresenter(IUserRegister.View view) {
        this.view = view;
    }


    @Override
    public void registrarse(String environment, String name, String lastname, String dni, String email, String pssw, String com, String group) {


        JSONObject obj = new JSONObject();
        try {

            obj.put(ENV, environment);
            obj.put(NAME, name);
            obj.put(LAST_NAME, lastname);
            obj.put(DNI, dni);
            obj.put(EMAIL, email);
            obj.put(PSW, pssw);
            obj.put(COM, com);
            obj.put(GROUP, group);

            Intent i = new Intent((Context) view, ServiceHTTP_POST.class);
            i.putExtra(EVENT, LOG_EVENT);
            i.putExtra(URI_KEY, URI_REGISTER_USER);
            i.putExtra(DATOS_JSON_KEY, obj.toString());

            ((Context) view).startService(i);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
