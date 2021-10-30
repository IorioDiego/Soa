package com.example.covid.presenters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.covid.ServiceHTTP_POST;
import com.example.covid.interfaces.IPatronDesbloqueo;
import com.example.covid.interfaces.IUserLogin;

import org.json.JSONException;
import org.json.JSONObject;

public class UserLoginPresenter  implements  IUserLogin.Presenter{

    private IUserLogin.View view;
    private static final String URI_LOGIN_USER = "http://so-unlam.net.ar/api/api/login";
    private static final String URI_REGISTER_EVENT = "http://so-unlam.net.ar/api/api/event";


        public UserLoginPresenter(IUserLogin.View view){
            this.view = view;
        }

        @Override
        public void loguearse(String email, String pssw) {

            ConnectivityManager connectivityManager = (ConnectivityManager) view.getSystemService();
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                // Si hay conexión a Internet en este momento
                Toast.makeText(view.getContexto(),"BUENA conexion de la red",Toast.LENGTH_SHORT).show();

                JSONObject obj = new JSONObject();
                try {

                    obj.put("email", email);
                    obj.put("password", pssw);


                    Intent i = new Intent((Context) view, ServiceHTTP_POST.class);
                    i.putExtra("evento","log");
                    i.putExtra("uri",URI_LOGIN_USER);
                    i.putExtra("datosJson",obj.toString());
                    ((Context) view).startService(i);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                int i;
            } else {
                Toast.makeText(view.getContexto(),"Error de conexion a la red",Toast.LENGTH_SHORT).show();
            }


        }

    @Override
    public void registrarEvento(String env, String event, String desc, String token) {

        JSONObject obj = new JSONObject();
        try {

            obj.put("env", env);
            obj.put("type_events", event);
            obj.put("description", desc);


            Intent i = new Intent((Context) view,ServiceHTTP_POST.class);
            i.putExtra("evento","RegistrarEvento");
            i.putExtra("uri",URI_REGISTER_EVENT);
            i.putExtra("datosJson",obj.toString());
            i.putExtra("tokenEvento",token);

            ((Context) view).startService(i);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
