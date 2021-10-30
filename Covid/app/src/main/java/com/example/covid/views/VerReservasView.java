package com.example.covid.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.covid.R;
import com.example.covid.interfaces.IVerReservas;
import com.example.covid.presenters.VerReservasPresenter;

public class VerReservasView extends AppCompatActivity implements IVerReservas.View {

    Button verReservas;
    String user;
    private IVerReservas.Presenter presenter;
    String userTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_reservas);
        presenter  = (IVerReservas.Presenter) new VerReservasPresenter(this);
        verReservas = (Button) findViewById(R.id.consultaReservas);
        verReservas.setOnClickListener(handleBtnVerReservas);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userTxt = extras.getString("user");
    }

    @Override
    public Context getContexto() {
        return getApplicationContext();
    }

    @Override
    public Object getSystemService() {
        return null;
    }

    private View.OnClickListener handleBtnVerReservas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.verReservas(userTxt);
        }
    };
}