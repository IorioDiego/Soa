package com.example.covid.presenters;

import android.content.Context;
import android.content.Intent;

import com.example.covid.ServiceHTTP_POST;
import com.example.covid.ServiceHTTP_PUT;
import com.example.covid.interfaces.IMenu;
import com.example.covid.interfaces.IUserLogin;

public class MenuPresenter implements IMenu.Presenter {

    private static final String URI_REFRESH = "http://so-unlam.net.ar/api/api/refresh";
    private IMenu.View view;

    public MenuPresenter(IMenu.View view) {
        this.view = view;
    }

    @Override
    public void actualizarToken(String etiqueta, String url, String token_refresh) {

        Intent i = new Intent((Context) view, ServiceHTTP_PUT.class);

        i.putExtra("url", URI_REFRESH);
        i.putExtra("token_refresh", token_refresh);
        ((Context) view).startService(i);
    }
}
