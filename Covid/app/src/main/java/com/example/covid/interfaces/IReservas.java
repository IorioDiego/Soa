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
    }

    interface Model{
        void makeReservation(String client, Date date, Context c);
        List<String> getReservas(String user, Context c);
    }

}
