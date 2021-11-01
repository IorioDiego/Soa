package com.example.covid.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.covid.MyAdapter;
import com.example.covid.R;
import com.example.covid.interfaces.IListaLogs;
import com.example.covid.interfaces.IPatronDesbloqueo;
import com.example.covid.presenters.ListaLogsPresenter;
import com.example.covid.presenters.PatronDesbloquePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListaLogsView extends AppCompatActivity implements  IListaLogs.View  {

    private  ListView  logList;


    private  IListaLogs.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_logs_view);
        presenter = new ListaLogsPresenter(this);

        logList =findViewById(R.id.logList);
        HashMap<String,String> logMap = (HashMap<String, String>) presenter.leerCantDeLogueos(getApplicationContext());
        MyAdapter adapter = new MyAdapter(logMap);
        logList.setAdapter(adapter);

    }
}