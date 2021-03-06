package com.example.covid.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.covid.R;
import com.example.covid.ServiceHTTP_POST;
import com.example.covid.ServiceHTTP_PUT;
import com.example.covid.interfaces.IReservas;
import com.example.covid.presenters.ReservasPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservasView1 extends AppCompatActivity implements IReservas.View, SensorEventListener {


    private IReservas.Presenter presenter;
    private Button reservarButton;
    private Button listShakeButton;
    private CalendarView calendarView;
    private Date currentDate;
    private String userTxt;
    private SensorManager sensores;
    private String token;
    private String token_refresh;

    private static final String ENV = "PROD", EVENTO_RESERVA = "Evento_Reserva",
            DATOS_JSON_KEY = "datosJson",
            USER_KEY = "user",
            TOKEN_KEY = "token",
            TOKEN_REFRESH_KEY = "token_refresh",
            ERROR_DE_CONEXION = "Error en la conexion de red",
            POST_EVENTO = "com.example.intentservice.intent.action.POST_EVENTO",
            RESPUESTA_PUT = "com.example.intentservice.intent.action.RESPUESTA_PUT",
            SUCCESS_KEY = "success",
            REFRESH_KEY = "refresh",
            TIMEOUT_KEY = "timeout",
            FALSE_KEY = "false";
    private static final int SHAKE_THRESHOLD = 800;
    public IntentFilter filtro;
    private RecepetorEvento receiverEvento = new RecepetorEvento();
    private RecepetorRefresh receiverRefresh = new RecepetorRefresh();

    int cont=0;


    private static final int FORCE_THRESHOLD = 350;
    private static final int TIME_THRESHOLD = 100;
    private static final int SHAKE_TIMEOUT = 500;
    private static final int SHAKE_DURATION = 1000;
    private static final int SHAKE_COUNT = 3;

    private float mLastX=-1.0f, mLastY=-1.0f, mLastZ=-1.0f;
    private long mLastTime;
    private int mShakeCount = 0;
    private long mLastShake;
    private long mLastForce;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);
        presenter = (IReservas.Presenter) new ReservasPresenter(this);
        calendarView = (CalendarView) findViewById(R.id.calendarReserva);
        reservarButton = (Button) findViewById(R.id.reservarButton);
        listShakeButton = (Button) findViewById(R.id.btnShakeList);

        calendarView.setOnDateChangeListener(handleDateChanged);
        reservarButton.setOnClickListener(handleBtnReservar);
        listShakeButton.setOnClickListener(handleBtnShakeList);
        sensores = (SensorManager) getSystemService(SENSOR_SERVICE);
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        currentDate = new Date();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();


        userTxt = extras.getString(USER_KEY);
        token = extras.getString(TOKEN_KEY);
        token_refresh = extras.getString(TOKEN_REFRESH_KEY);

        Ini_Sensores();
    }

    private View.OnClickListener handleBtnReservar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {

                presenter.postearReserva(ENV, EVENTO_RESERVA, "El usuario hizo una reserva para " + currentDate.toString(), token, token_refresh);

            } else {
                Toast.makeText(getApplicationContext(), ERROR_DE_CONEXION, Toast.LENGTH_SHORT).show();
            }

        }
    };

    private View.OnClickListener handleBtnShakeList = new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            Intent in = new Intent(ReservasView1.this, ListaShake.class);
            in.putExtra(USER_KEY, userTxt.toString());
            startActivity(in);

        }
    };


    protected void Ini_Sensores() {
        sensores.registerListener(this, sensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        sensores.registerListener(this, sensores.getDefaultSensor(Sensor.TYPE_PROXIMITY), SensorManager.SENSOR_DELAY_NORMAL);


    }

    // Metodo para parar la escucha de los sensores
    private void Parar_Sensores() {
        sensores.unregisterListener((SensorEventListener) this, sensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        sensores.unregisterListener((SensorEventListener) this, sensores.getDefaultSensor(Sensor.TYPE_PROXIMITY));
    }

    public CalendarView.OnDateChangeListener handleDateChanged = new CalendarView.OnDateChangeListener() {
        @Override
        public void onSelectedDayChange(CalendarView view, int year, int month,
                                        int dayOfMonth) {
            currentDate = new Date(year, month, dayOfMonth);
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {

        synchronized (this) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    long now = System.currentTimeMillis();
                    if ((now - mLastForce) > SHAKE_TIMEOUT) {
                        mShakeCount = 0;
                    }

                    if ((now - mLastTime) > TIME_THRESHOLD) {
                        long diff = now - mLastTime;
                        float speed = Math.abs(event.values[0] + event.values[1] +event.values[2] - mLastX - mLastY - mLastZ) / diff * 10000;
                        if (speed > FORCE_THRESHOLD) {
                            if ((++mShakeCount >= SHAKE_COUNT) && (now - mLastShake > SHAKE_DURATION)) {
                                mLastShake = now;
                                mShakeCount = 0;


                                presenter.postearReserva(ENV, EVENTO_RESERVA, "El usuario hizo una reserva para " + currentDate.toString(), token, token_refresh);
                                presenter.RegistrarCantidadShakes(getApplicationContext(), userTxt);
                            }
                            mLastForce = now;
                        }
                        mLastTime = now;
                        mLastX = event.values[0];
                        mLastY =event.values[1];
                        mLastZ = event.values[2];
                    }




                    break;
                case Sensor.TYPE_PROXIMITY:
                    if (event.values[0] <= 1) {
                        float e = event.sensor.getMaximumRange();
                        onBackPressed();
                    }
                    break;
            }
        }
    }

    private void configurarBroadcastRecieverPostEvento() {
        filtro = new IntentFilter(POST_EVENTO);
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiverEvento, filtro);
    }

    public class RecepetorEvento extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {


            try {
                String datosJsonString = intent.getStringExtra(DATOS_JSON_KEY);

                JSONObject datosJson = new JSONObject(datosJsonString);
                String resultadoRequest = datosJson.getString(SUCCESS_KEY);

                if (resultadoRequest == FALSE_KEY) {

                    String code = intent.getStringExtra(TIMEOUT_KEY);

                    if (Integer.parseInt(code) == HttpURLConnection.HTTP_UNAUTHORIZED) {
                        presenter.actulizarToken(token_refresh);
                    }
                }else{
                    presenter.reservar(currentDate, userTxt, getApplicationContext());
                    Toast.makeText(getApplicationContext(), "Se reserv?? un lugar correctamente", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }


    private void configurarBroadcastRecieverRefresh() {
        filtro = new IntentFilter(RESPUESTA_PUT);
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiverRefresh, filtro);
    }

    public class RecepetorRefresh extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            try {
                String datosJsonString = intent.getStringExtra(REFRESH_KEY);
                JSONObject datosJson = new JSONObject(datosJsonString);
                token = datosJson.getString(SUCCESS_KEY);
                token_refresh = datosJson.getString(TOKEN_KEY);
                token_refresh = datosJson.getString(TOKEN_REFRESH_KEY);
                presenter.postearReserva(ENV, EVENTO_RESERVA, "El usuario hizo una reserva para " + currentDate.toString(), token, token_refresh);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Override
    protected void onStop() {

        Parar_Sensores();
        configurarBroadcastRecieverPostEvento();
        configurarBroadcastRecieverRefresh();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(receiverRefresh);
        unregisterReceiver(receiverEvento);
        stopService(new Intent(this, ServiceHTTP_PUT.class));
        stopService(new Intent(this, ServiceHTTP_POST.class));
        Parar_Sensores();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        Parar_Sensores();
        unregisterReceiver(receiverRefresh);
        unregisterReceiver(receiverEvento);
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Ini_Sensores();
        configurarBroadcastRecieverPostEvento();
        configurarBroadcastRecieverRefresh();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        configurarBroadcastRecieverPostEvento();
        configurarBroadcastRecieverRefresh();
        Ini_Sensores();
    }
}