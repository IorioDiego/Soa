package com.example.covid.interfaces;


import android.content.Context;

import java.util.Date;
import java.util.List;

public interface IReservas {

    interface View{

        Context getContexto();

        Object getSystemService();
    }

    interface  Presenter {
        void reservar(Date fechaSelec, String cliente );

        void postearReserva(String env, String evento, String desc, String token, String token_refresh);

        void actulizarToken(String token_refresh);

        void RegistrarCantidadShakes(Context applicationContext,String user);
    }

    interface Model{
        void makeReservation(String client, Date date, Context c);
        void RegistrarCantidadShakes(Context c,String user);
        List<String> getReservas(String user, Context c);
    }

}
