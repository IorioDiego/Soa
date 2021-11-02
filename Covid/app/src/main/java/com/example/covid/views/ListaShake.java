package com.example.covid.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.example.covid.MyAdapter;
import com.example.covid.R;
import com.example.covid.interfaces.IListaShake;
import com.example.covid.presenters.ListaShakePresenter;

import java.util.HashMap;

public class ListaShake extends AppCompatActivity implements IListaShake.View {

    private ListView logShake;
    private TextView sinShakeText;

    private IListaShake.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_shakes);
        sinShakeText = findViewById(R.id.sinShakesTxtView);
        presenter = new ListaShakePresenter(this);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        String user = extras.getString("user");
        logShake = findViewById(R.id.shakeList);
        HashMap<String, String> shakeMap = (HashMap<String, String>) presenter.leerCantDeShakes(getApplicationContext(), user);
        if (shakeMap.isEmpty()) {
            sinShakeText.setText("No hay Shakes registrados");
        }
        MyAdapter adapter = new MyAdapter(shakeMap);
        logShake.setAdapter(adapter);
    }
}