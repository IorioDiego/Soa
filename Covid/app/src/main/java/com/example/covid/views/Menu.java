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
import com.example.covid.Timer;
import com.example.covid.interfaces.IMenu;

import org.json.JSONException;
import org.json.JSONObject;

public class Menu extends AppCompatActivity implements IMenu.View {

    private  String token;
    private String token_refresh;
    private IMenu.Presenter presenter;
    private  String nomrbeReceiverTimer="TIMER_ACT_MENU";
    String userTxt;
    Button reservar, verReservas;
    private ReceptorTimer receiverTimer = new ReceptorTimer();
    private ReceptorActualizacion receiverRefresh= new ReceptorActualizacion();
    public IntentFilter filtro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();

        token= extras.getString("token");
        token_refresh= extras.getString("token_refresh");

        userTxt = extras.getString("user");
        reservar = (Button) findViewById(R.id.buttonReservar);
        verReservas = (Button) findViewById(R.id.buttonVerReservas);
        reservar.setOnClickListener(handleBtonReservar);
        verReservas.setOnClickListener(handleBtnVerReservas);



        //la idea es q aca se inicie y despues cuando se para la app se frene y actualice
     //   configurarBroadcastRecieverTimer();
        startService(new Intent(Menu.this, Timer.class));

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

    private void configurarBroadcastRecieverTimer() {
        filtro = new IntentFilter( "com.example.intentservice.intent.action.TIMER_ACT");
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiverTimer,filtro);
    }

    public class ReceptorTimer extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            String finTimer  = intent.getStringExtra("Fin");
            Toast.makeText(getApplicationContext(),"Se termino el tiempo",Toast.LENGTH_SHORT).show();



            //cierra el servicio ya que no es necesario mantenerlo, sera creado al pulsar el boton nuevamente
            stopService(new Intent(Menu.this, Timer.class));

            startService(new Intent(Menu.this, Timer.class));


        }
    }

    private void configurarBroadcastRecieverRefresh() {
        filtro = new IntentFilter( "com.example.intentservice.intent.action.RESPUESTA_PUT");
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiverRefresh,filtro);
    }


    public class ReceptorActualizacion extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
            String resultadoRefresh  = intent.getStringExtra("refresh");
            JSONObject resultadoRefreshJason = null;
            try {
                resultadoRefreshJason = new JSONObject(resultadoRefresh);

                String success = resultadoRefreshJason.getString("success");

                if(success.equals("true")){
                    token = resultadoRefreshJason.getString("token");
                    token_refresh=resultadoRefreshJason.getString("token_refresh");


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }




            //cierra el servicio ya que no es necesario mantenerlo, sera creado al pulsar el boton nuevamente
            stopService(new Intent(Menu.this, Timer.class));

            startService(new Intent(Menu.this, Timer.class));


        }
    }

    @Override
    protected void onStop()
    {




        super.onStop();
    }



    @Override
    protected void onDestroy()
    {
        //unregisterReceiver(receiverTimer);


        super.onDestroy();
    }

    @Override
    protected void onPause()
    {unregisterReceiver(receiverTimer);

        super.onPause();
    }

    @Override
    protected void onRestart()
    {//Actualizar token y reempezar timer


        super.onRestart();
    }

    @Override
    protected void onResume()
    {//Actualizar token y reempezar timer
        configurarBroadcastRecieverTimer();

        super.onResume();


    }



}