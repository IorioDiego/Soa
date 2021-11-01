package com.example.covid.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.covid.MyAdapter;
import com.example.covid.R;
import com.example.covid.interfaces.IListaShake;
import com.example.covid.presenters.ListaShakePresenter;

import java.util.HashMap;

public class ListaShake extends AppCompatActivity implements IListaShake.View {

    private ListView logShake;

    private IListaShake.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_shakes);

        presenter = new ListaShakePresenter(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String user = extras.getString("user");
        logShake = findViewById(R.id.shakeList);
        HashMap<String, String> logMap = (HashMap<String, String>) presenter.leerCantDeShakes(getApplicationContext(), user);
        MyAdapter adapter = new MyAdapter(logMap);
        logShake.setAdapter(adapter);
    }
}