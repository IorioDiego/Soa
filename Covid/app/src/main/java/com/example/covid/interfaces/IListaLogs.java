package com.example.covid.interfaces;

import android.content.Context;

import java.util.Map;

public interface IListaLogs {

    interface View {

    }

    interface Presenter {

        Map<String, String> leerCantDeLogueos(Context c);
    }

    interface Model {

        Map<String, String> leerCantDeLogueos(Context c);
    }

}
