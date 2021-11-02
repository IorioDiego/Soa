package com.example.covid.presenters;


import android.content.Context;

import com.example.covid.interfaces.IListaLogs;
import com.example.covid.models.ListaLogsModel;

import java.util.Map;

public class ListaLogsPresenter implements IListaLogs.Presenter {

    private IListaLogs.View view;
    private IListaLogs.Model model;

    public ListaLogsPresenter(IListaLogs.View view) {
        this.view = view;
        model = new ListaLogsModel(this);
    }


    @Override
    public Map<String, String> leerCantDeLogueos(Context c) {
        return model.leerCantDeLogueos(c);
    }
}
