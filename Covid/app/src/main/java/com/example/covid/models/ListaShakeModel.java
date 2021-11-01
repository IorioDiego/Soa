package com.example.covid.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.covid.interfaces.IListaLogs;
import com.example.covid.interfaces.IListaShake;

import java.util.Map;

public class ListaShakeModel implements IListaShake.Model {
    private IListaShake.Presenter presenter;

    public ListaShakeModel(IListaShake.Presenter presenter){
        this.presenter = presenter;
    }
    @Override
    public Map<String, String> leerCantDeShakes(Context c,String user) {
        SharedPreferences preferencias = c.getSharedPreferences(user+"Shakes", Context.MODE_PRIVATE);
        return  (Map<String, String>) preferencias.getAll();
    }
}
