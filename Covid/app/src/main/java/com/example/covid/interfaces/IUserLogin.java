package com.example.covid.interfaces;

import android.content.Context;

public interface IUserLogin {

    interface View{

        Context getContexto();

        Object getSystemService();
    }

    interface  Presenter {
        void loguearse(String email, String pssw );
        void registrarEvento(String env, String event, String desc,String token,String tokenRefresh);
        void registrarCantidadLogueos(Context c);
        String leerCantDeLogueos(Context c);
    }

    interface  Model{
        void registrarCantidadLogueos(Context c);
        String leerCantDeLogueos(Context c);
    }

}
