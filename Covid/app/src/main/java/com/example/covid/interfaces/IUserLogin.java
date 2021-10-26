package com.example.covid.interfaces;

import android.content.Context;

public interface IUserLogin {

    interface View{

        Context getContexto();

        Object getSystemService();
    }

    interface  Presenter {
        void loguearse(String email, String pssw );

    }

}
