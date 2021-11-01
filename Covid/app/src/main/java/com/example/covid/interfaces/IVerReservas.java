package com.example.covid.interfaces;

import android.content.Context;
import android.os.CpuUsageInfo;
import android.widget.TableLayout;

import java.util.List;

public interface IVerReservas {

    interface View{

        Context getContexto();

        Object getSystemService();
    }

    interface Presenter{
        public List<String> verReservas(String user, Context c);
    }

    interface Model{
        public List<String> getReservas(String user,Context c);
    }

}
