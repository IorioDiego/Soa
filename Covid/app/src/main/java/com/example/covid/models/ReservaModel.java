package com.example.covid.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.covid.interfaces.IReservas;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ReservaModel implements IReservas.Model {
    @Override
    public void makeReservation(String client, Date date, Context c) {
        SharedPreferences preferencias= c.getSharedPreferences("reservas" + client, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferencias.edit();





            String uuid = UUID.randomUUID().toString();

            editor.putString(uuid, date.toString());

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
    /*    while(n != null){
            listDates.add(n);
            n = preferencias.getString("fecha", null);
        }*/

        return listDates;
    }


}
