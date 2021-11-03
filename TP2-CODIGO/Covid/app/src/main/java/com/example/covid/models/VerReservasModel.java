package com.example.covid.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.covid.interfaces.IVerReservas;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class VerReservasModel implements IVerReservas.Model {

    private  IVerReservas.Presenter presenter;
    private static final String filenameReservas = "reservas";
    public  VerReservasModel(IVerReservas.Presenter presenter){
        this.presenter = presenter;
    }
    @Override
    public List<String> getReservas(String user, Context c) {
        List<String> listDates = new ArrayList<>();
        SharedPreferences preferencias = c.getSharedPreferences(filenameReservas + user, Context.MODE_PRIVATE);

        Map<String,String> m = (Map<String, String>) preferencias.getAll();
        for (Map.Entry<String, String> reserva :m.entrySet()  ) {
            listDates.add(reserva.getValue().toString());

        }
        return listDates;
    }
}
