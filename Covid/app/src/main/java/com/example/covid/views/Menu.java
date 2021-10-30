package com.example.covid.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.covid.R;

public class Menu extends AppCompatActivity {

    String userTxt;
    Button reservar, verReservas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userTxt = extras.getString("user");
        reservar = (Button) findViewById(R.id.buttonReservar);
        verReservas = (Button) findViewById(R.id.buttonVerReservas);
        reservar.setOnClickListener(handleBtonReservar);
        verReservas.setOnClickListener(handleBtnVerReservas);
    }

    private View.OnClickListener handleBtnVerReservas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Menu.this, VerReservasView.class);
            intent.putExtra("user", userTxt);
            startActivity(intent);
            finish();
        }
    };

    private View.OnClickListener handleBtonReservar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Menu.this, ReservasView1.class);
            intent.putExtra("user", userTxt);
            startActivity(intent);
            finish();

        }
    };
}