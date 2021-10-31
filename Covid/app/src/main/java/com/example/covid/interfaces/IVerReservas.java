package com.example.covid.interfaces;

import android.content.Context;
import android.widget.TableLayout;

import java.util.List;

public interface IVerReservas {

    interface View{

        Context getContexto();

        Object getSystemService();
    }

    interface Presenter{
        public List<String> verReservas(String user);
    }

}
