package com.example.covid.models;


import android.content.Context;
import android.content.SharedPreferences;

import com.example.covid.interfaces.IUserLogin;
import com.example.covid.views.UserLogin;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UserLoginModel implements IUserLogin.Model {

    private  IUserLogin.Presenter presenter;


    public UserLoginModel(IUserLogin.Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    public void registrarCantidadLogueos(Context c) {
        Integer l=0;

        SharedPreferences preferencias= c.getSharedPreferences("logueos" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        long hoy = System.currentTimeMillis();
        Date fechaHoy = new Date(hoy);
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        String fechaDF = df.format(fechaHoy);

        String cantLogs = preferencias.getString(fechaDF, null);
        if(cantLogs == null){
            l=1;
        }else
        {
            l = Integer.parseInt(cantLogs);
            l++;
        }

        editor.putString(fechaDF, l.toString());
        editor.commit();
    }

    @Override
    public String leerCantDeLogueos(Context c) {

        SharedPreferences preferencias= c.getSharedPreferences("logueos" , Context.MODE_PRIVATE);
        long hoy = System.currentTimeMillis();
        Date fechaHoy = new Date(hoy);
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        String fechaDF = df.format(fechaHoy);

        String cantLogs = preferencias.getString(fechaDF, null);
        if (cantLogs == null)
        {
            return  "0";
        }
        else
        {
            return  cantLogs;
        }


    }
}
