package com.example.covid.interfaces;

import android.content.Context;

import java.util.Map;

public interface IListaShake {


    interface View{

    }

    interface  Presenter {

        Map<String,String> leerCantDeShakes(Context c,String user);
    }

    interface  Model{

        Map<String,String> leerCantDeShakes(Context c,String user);
    }

}
