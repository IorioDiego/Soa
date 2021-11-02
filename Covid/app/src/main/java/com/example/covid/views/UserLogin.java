package com.example.covid.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid.R;

import com.example.covid.ServiceHTTP_POST;
import com.example.covid.UserRegister;
import com.example.covid.interfaces.IUserLogin;
import com.example.covid.presenters.UserLoginPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class UserLogin extends AppCompatActivity implements IUserLogin.View {


    Button btnRegistro;
    Button btnLogIn;
    Button btnListaLogs;

    private EditText txtEmail;
    private EditText txtPssw;
    private TextView txtViewLogs;

    private SensorManager sensores;


    private IUserLogin.Presenter presenter;

    public IntentFilter filtro;
    private ReceptorOperacion receiverReg = new ReceptorOperacion();
    private RecpetorEvento receiverEvento = new RecpetorEvento();
    String token;
    String tokenRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        presenter = (IUserLogin.Presenter) new UserLoginPresenter(this);

        txtViewLogs = (TextView) findViewById((R.id.textViewLogs));
        String l = presenter.leerCantDeLogueos(getApplicationContext());
        txtViewLogs.setText(l);
        txtEmail = (EditText) findViewById(R.id.editTextLoginEmail);
        txtPssw = (EditText) findViewById(R.id.editTextLoginPssw);

        btnRegistro = (Button) findViewById(R.id.buttonRegistro);
        btnRegistro.setOnClickListener(HandlerBtnRegistro);

        btnLogIn = (Button) findViewById(R.id.buttonLogIn);
        btnLogIn.setOnClickListener(HandlerBtnLogIn);

        btnListaLogs = (Button) findViewById(R.id.btnListaLog);
        btnListaLogs.setOnClickListener(HandlerBtnListar);

        sensores = (SensorManager) getSystemService(SENSOR_SERVICE);
        configurarBroadcastReciever();

        configurarBroadcastRecieverPostEvento();


        //Chequeo bateria
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        int batteryPct = level * 100 / scale;

        Toast.makeText(getApplicationContext(), "Nivel de bateria: " + batteryPct + "%", Toast.LENGTH_SHORT).show();
        // chequeo bateria*/

    }


    private void configurarBroadcastRecieverPostEvento() {
        filtro = new IntentFilter("com.example.intentservice.intent.action.POST_EVENTO");
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiverEvento, filtro);
    }


    private void configurarBroadcastReciever() {
        filtro = new IntentFilter("com.example.intentservice.intent.action.RESPUESTA_OPERACION");
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiverReg, filtro);
    }

    private View.OnClickListener HandlerBtnRegistro = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UserLogin.this, UserRegister.class);

            startActivity(intent);
        }
    };

    private View.OnClickListener HandlerBtnLogIn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                presenter.loguearse(txtEmail.getText().toString(), txtPssw.getText().toString());
            } else {
                Toast.makeText(getApplicationContext(), "Error de conexion a la red", Toast.LENGTH_SHORT).show();
            }

        }
    };

    private View.OnClickListener HandlerBtnListar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            Intent i = new Intent(UserLogin.this, ListaLogsView.class);
            startActivity(i);


        }
    };


    public class ReceptorOperacion extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {


            try {
                String datosJsonString = intent.getStringExtra("datosJson");
                JSONObject datosJson = new JSONObject(datosJsonString);
                //Toast.makeText(getApplicationContext(),"Se recibido respuesta del server",Toast.LENGTH_SHORT).show();
                String resultadoRequest = datosJson.getString("success");

                if (resultadoRequest.equals("true")) {
                    token = datosJson.getString("token");
                    tokenRefresh = datosJson.getString("token_refresh");
                    presenter.registrarEvento("TEST", "EVENTO_LOGUEO", "El usuario se logueo en el sistema", token, tokenRefresh);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class RecpetorEvento extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {


            try {
                String datosJsonString = intent.getStringExtra("datosJson");
                JSONObject datosJson = new JSONObject(datosJsonString);

                Log.i("LOGUEO_MAIN", "Datos Json Main Thread" + datosJsonString);

                //txtResultado.setText(datosJsonString);

                String resultadoRequest = datosJson.getString("success");

                if (resultadoRequest == "true") {

                    Log.i("LOGUEO_MAIN", "TOKEN MAIN TRHEAD" + token);
                    presenter.registrarCantidadLogueos(getApplicationContext());

                    Intent i = new Intent(UserLogin.this, Menu.class);
                    i.putExtra("token", token);
                    i.putExtra("token_refresh", tokenRefresh);
                    i.putExtra("user", txtEmail.getText().toString());
                    startActivity(i);

                } else {
                    String msg = datosJson.getString("msg");
                    Log.i("LOGUEO_MAIN", msg);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onBackPressed() {
        Intent in = new Intent(Intent.ACTION_MAIN);
        in.addCategory(in.CATEGORY_HOME);
        startActivity(in);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiverEvento);
        unregisterReceiver(receiverReg);
        stopService(new Intent(this, ServiceHTTP_POST.class));
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        String l = presenter.leerCantDeLogueos(getApplicationContext());
        txtViewLogs.setText(l);
        super.onRestart();
    }

    @Override
    protected void onResume() {
        String l = presenter.leerCantDeLogueos(getApplicationContext());
        txtViewLogs.setText(l);
        super.onResume();
    }

}