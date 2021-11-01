package com.example.covid.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.covid.interfaces.IVerReservas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VerReservasModel implements IVerReservas.Model {

    private  IVerReservas.Presenter presenter;

    public  VerReservasModel(IVerReservas.Presenter presenter){
        this.presenter = presenter;
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
