package com.example.covid.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.covid.interfaces.IReservas;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReservaModel implements IReservas.Model {
    private  IReservas.Presenter presenter;



    public ReservaModel(IReservas.Presenter presenter){
        this.presenter = presenter;
    }


    @Override
    public void makeReservation(String client, Date date, Context c) {
        SharedPreferences preferencias= c.getSharedPreferences("reservas" + client, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        String uuid = UUID.randomUUID().toString();
        editor.putString(uuid, date.toString());
        editor.commit();
    }

    @Override
    public void RegistrarCantidadShakes(Context c,String user) {
        Integer s=0;

        SharedPreferences preferencias= c.getSharedPreferences(user+"Shakes" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();
        long hoy = System.currentTimeMillis();
        Date fechaHoy = new Date(hoy);
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        String fechaDF = df.format(fechaHoy);

        String cantLogs = preferencias.getString(fechaDF, null);
        if(cantLogs == null){
            s=1;
        }else
        {
            s = Integer.parseInt(cantLogs);
            s++;
        }

        editor.putString(fechaDF, s.toString());
        editor.commit();
    }

    @Override
    public List<String> getReservas(String user, Context c) {
        List<String> listDates = new ArrayList<>();
        SharedPreferences preferencias = c.getSharedPreferences("reservas"+user, Context.MODE_PRIVATE);
        String n = preferencias.getString("fecha", null);

        Map<String,String> m = (Map<String, String>) preferencias.getAll();
        for (Map.Entry<String, String> reserva :m.entrySet()  ) {
            listDates.add(reserva.getValue().toString());

        }
        return listDates;
    }


}
