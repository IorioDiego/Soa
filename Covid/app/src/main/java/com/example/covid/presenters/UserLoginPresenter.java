package com.example.covid.presenters;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.example.covid.ServiceHTTP_POST;
import com.example.covid.interfaces.IPatronDesbloqueo;
import com.example.covid.interfaces.IUserLogin;

import org.json.JSONObject;

public class UserLoginPresenter  implements  IUserLogin.Presenter{

    private IUserLogin.View view;
    private static final String URI_LOGIN_USER = "http://so-unlam.net.ar/api/api/login";



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

                    obj.put("env", email);
                    obj.put("env", pssw);


                    Intent i = new Intent((Context) view, ServiceHTTP_POST.class);
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



}
