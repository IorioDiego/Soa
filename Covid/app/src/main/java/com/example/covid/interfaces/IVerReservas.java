package com.example.covid.interfaces;

import android.content.Context;

public interface IVerReservas {

    interface View{

        Context getContexto();

        Object getSystemService();
    }

    interface Presenter{
        public void verReservas(String user);
    }

}
