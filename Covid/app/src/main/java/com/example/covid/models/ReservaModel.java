package com.example.covid.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.covid.interfaces.IReservas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReservaModel implements IReservas.Model {
    @Override
    public void makeReservation(String client, Date date, Context c) {
        SharedPreferences preferencias= c.getSharedPreferences("reservas" + client, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();

        editor.putString("fecha", date.toString());
        editor.putString("user", client);
    }

    @Override
    public List<String> getReservas(String user, Context c) {
        List<String> listDates = new ArrayList<>();
        SharedPreferences preferencias = c.getSharedPreferences("reservas"+user, Context.MODE_PRIVATE);
        String n = preferencias.getString("fecha", null);
        while(n != null){
            listDates.add(n);
            n = preferencias.getString("fecha", null);
        }

        return listDates;
    }


}
