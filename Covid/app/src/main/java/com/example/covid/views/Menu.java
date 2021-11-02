package com.example.covid.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.covid.R;
import com.example.covid.interfaces.IMenu;

import org.json.JSONException;
import org.json.JSONObject;

public class Menu extends AppCompatActivity implements IMenu.View {

    private String token;
    private String token_refresh;
    private IMenu.Presenter presenter;

    String userTxt;
    Button reservar, verReservas;

    private ReceptorActualizacion receiverRefresh = new ReceptorActualizacion();
    public IntentFilter filtro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        token = extras.getString("token");
        token_refresh = extras.getString("token_refresh");

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

        }
    };

    private View.OnClickListener handleBtonReservar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(Menu.this, ReservasView1.class);
            intent.putExtra("user", userTxt);
            startActivity(intent);


        }
    };


    private void configurarBroadcastRecieverRefresh() {
        filtro = new IntentFilter("com.example.intentservice.intent.action.RESPUESTA_PUT");
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiverRefresh, filtro);
    }


    public class ReceptorActualizacion extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String resultadoRefresh = intent.getStringExtra("refresh");
            JSONObject resultadoRefreshJason = null;
            try {
                resultadoRefreshJason = new JSONObject(resultadoRefresh);

                String success = resultadoRefreshJason.getString("success");

                if (success.equals("true")) {
                    token = resultadoRefreshJason.getString("token");
                    token_refresh = resultadoRefreshJason.getString("token_refresh");


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onStop() {


        super.onStop();
    }


    @Override
    protected void onDestroy() {


        super.onDestroy();
    }

    @Override
    protected void onPause() {

        super.onPause();
    }

    @Override
    protected void onRestart() {


        super.onRestart();
    }

    @Override
    protected void onResume() {

        super.onResume();


    }


}