package com.example.covid.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.covid.R;
import com.example.covid.interfaces.IReservas;
import com.example.covid.presenters.ReservasPresenter;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservasView1 extends AppCompatActivity implements IReservas.View, SensorEventListener {


    private IReservas.Presenter presenter;
    private Button reservarButton;
    private CalendarView calendarView;
    private Date currentDate;
    private String userTxt;
    private SensorManager sensores;
    DecimalFormat dosdecimales = new DecimalFormat("###.###");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);
        presenter  = (IReservas.Presenter) new ReservasPresenter(this);
        calendarView = (CalendarView) findViewById(R.id.calendarReserva);
        reservarButton = (Button) findViewById(R.id.reservarButton);

        calendarView.setOnDateChangeListener(handleDateChanged);
        reservarButton.setOnClickListener(handleBtnReservar);
        sensores = (SensorManager) getSystemService(SENSOR_SERVICE);
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        currentDate = new Date();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userTxt = extras.getString("user");
        Ini_Sensores();
    }

    private View.OnClickListener handleBtnReservar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.reservar(currentDate, userTxt);
        }
    };


    protected void Ini_Sensores()
    {
        sensores.registerListener(this, sensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),   SensorManager.SENSOR_DELAY_NORMAL);
    }

    // Metodo para parar la escucha de los sensores
    private void Parar_Sensores()
    {
        sensores.unregisterListener((SensorEventListener) this, sensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
    }

    public CalendarView.OnDateChangeListener handleDateChanged = new CalendarView.OnDateChangeListener(){
        @Override
        public void onSelectedDayChange(CalendarView view, int year, int month,
                                        int dayOfMonth) {
            currentDate = new Date(year, month, dayOfMonth);
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        String txt = "";


        synchronized (this)
        {
            Log.d("sensor", event.sensor.getName());

            switch(event.sensor.getType())
            {
                case Sensor.TYPE_ACCELEROMETER :
                    txt += "Acelerometro:\n";
                    txt += "x: " + dosdecimales.format(event.values[0]) + " m/seg2 \n";
                    txt += "y: " + dosdecimales.format(event.values[1]) + " m/seg2 \n";
                    txt += "z: " + dosdecimales.format(event.values[2]) + " m/seg2 \n";

                    if ((Math.abs(event.values[0]) > 30) || (Math.abs(event.values[1]) > 30) || (Math.abs(event.values[2]) > 30)) {
                        Toast.makeText(getContexto(), "Reservo", Toast.LENGTH_SHORT).show();
                        presenter.reservar(currentDate, userTxt);

                    }
                    break;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public Context getContexto() {
        return  getApplicationContext();
    }

    @Override
    public Object getSystemService() {
        return  getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    protected void onStop()
    {// stopService(new Intent(UserLogin.this, Timer.class));

        Parar_Sensores();

        super.onStop();
    }

    @Override
    protected void onDestroy()
    {// stopService(new Intent(UserLogin.this, Timer.class));
        Parar_Sensores();
        super.onDestroy();
    }

    @Override
    protected void onPause()
    {// stopService(new Intent(UserLogin.this, Timer.class));
        Parar_Sensores();

        super.onPause();
    }

    @Override
    protected void onRestart()
    {
        Ini_Sensores();

        super.onRestart();
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        Ini_Sensores();
    }
}