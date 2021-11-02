package com.example.covid.presenters;

import android.content.Context;

import com.example.covid.interfaces.IListaLogs;
import com.example.covid.interfaces.IListaShake;
import com.example.covid.models.ListaLogsModel;
import com.example.covid.models.ListaShakeModel;

import java.util.Map;

public class ListaShakePresenter implements IListaShake.Presenter {


    private IListaShake.View view;
    private IListaShake.Model model;

    public ListaShakePresenter(IListaShake.View view) {
        this.view = (IListaShake.View) view;
        model = (IListaShake.Model) new ListaShakeModel(this);
    }

    @Override
    public Map<String, String> leerCantDeShakes(Context c, String user) {
        return model.leerCantDeShakes(c, user);
    }
}
