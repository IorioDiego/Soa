package com.example.covid.models;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.covid.interfaces.IListaLogs;
import com.example.covid.interfaces.IPatronDesbloqueo;
import com.example.covid.presenters.ListaLogsPresenter;
import com.example.covid.views.ListaLogsView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListaLogsModel implements IListaLogs.Model {
    private IListaLogs.Presenter presenter;
    private static final String fileName = "logueos";
    public ListaLogsModel(IListaLogs.Presenter presenter){
        this.presenter = presenter;
    }

    @Override
    public Map<String, String> leerCantDeLogueos(Context c) {

        SharedPreferences preferencias = c.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return  (Map<String, String>) preferencias.getAll();
    }
}
