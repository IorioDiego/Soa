package com.example.covid.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.covid.R;
import com.example.covid.Test;
import com.example.covid.Timer;
import com.example.covid.UserRegister;
import com.example.covid.interfaces.IUserLogin;
import com.example.covid.presenters.UserLoginPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class UserLogin extends AppCompatActivity implements IUserLogin.View ,SensorEventListener {


    Button btnRegistro ;
    Button btnLogIn ;

    private EditText txtEmail;
    private EditText txtPssw;

    private SensorManager sensores;

    DecimalFormat dosdecimales = new DecimalFormat("###.###");



    private IUserLogin.Presenter presenter;

    public  IntentFilter filtro;
    private ReceptorOperacion receiverReg = new ReceptorOperacion();
    private ReceptorTimer receiverTimer = new ReceptorTimer();
    private RecpetorEvento receiverEvento = new RecpetorEvento();
    String token;
    String tokenRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        presenter  = (IUserLogin.Presenter) new UserLoginPresenter(this);

        txtEmail = (EditText) findViewById(R.id.editTextLoginEmail);
        txtPssw = (EditText) findViewById(R.id.editTextLoginPssw);

        btnRegistro = (Button) findViewById(R.id.buttonRegistro);
        btnRegistro.setOnClickListener(HandlerBtnRegistro);

        btnLogIn = (Button) findViewById(R.id.buttonLogIn);
        btnLogIn.setOnClickListener(HandlerBtnLogIn);
        sensores = (SensorManager) getSystemService(SENSOR_SERVICE);
        configurarBroadcastReciever();
       // configurarBroadcastRecieverTimer();
        configurarBroadcastRecieverPostEvento();


       //Chequeo bateria
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = this.registerReceiver(null, ifilter);
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

        int batteryPct = level * 100 /scale;

        Toast.makeText(getApplicationContext(),"Nivel de bateria: " + batteryPct + "%",Toast.LENGTH_SHORT).show();
        // chequeo bateria*/

        //cheqoe internet
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            // Si hay conexión a Internet en este momento
            Toast.makeText(getApplicationContext(),"BUENA conexion de la red",Toast.LENGTH_SHORT).show();
            int i;
        } else {
            Toast.makeText(getApplicationContext(),"Error de conexion a la red",Toast.LENGTH_SHORT).show();
        }

        //


    }

    protected void Ini_Sensores()
    {
        sensores.registerListener(this, sensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),   SensorManager.SENSOR_DELAY_NORMAL);
        sensores.registerListener(this, sensores.getDefaultSensor(Sensor.TYPE_PROXIMITY),   SensorManager.SENSOR_DELAY_NORMAL);

    }

    // Metodo para parar la escucha de los sensores
    private void Parar_Sensores()
    {

        sensores.unregisterListener((SensorEventListener) this, sensores.getDefaultSensor(Sensor.TYPE_ACCELEROMETER));
        sensores.unregisterListener((SensorEventListener) this, sensores.getDefaultSensor(Sensor.TYPE_PROXIMITY));

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy)
    {

    }

    // Metodo que escucha el cambio de los sensores
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        String txt = "";

        // Cada sensor puede lanzar un thread que pase por aqui
        // Para asegurarnos ante los accesos simult�neos sincronizamos esto

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


                    if ((Math.abs(event.values[0]) > 30) || (Math.abs(event.values[1]) > 30) || (Math.abs(event.values[2]) > 30))
                    {
                        Toast.makeText(getApplicationContext(), "SE MOVIOOOOO", Toast.LENGTH_SHORT).show();
                    }
                    break;

                case Sensor.TYPE_PROXIMITY :
                    txt += "Proximidad:\n";
                    txt += event.values[0] + "\n";

                   ;

                    // Si detecta 0 lo represento
                    if( event.values[0]  < event.sensor.getMaximumRange() )
                    {
                        float e = event.sensor.getMaximumRange();
                        Toast.makeText(getApplicationContext(), "ME CIERRO", Toast.LENGTH_SHORT).show();
                    }
                    break;

            }
        }
    }


    private void configurarBroadcastRecieverPostEvento() {
        filtro = new IntentFilter( "com.example.intentservice.intent.action.POST_EVENTO");
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiverEvento,filtro);
    }


    private void configurarBroadcastRecieverTimer() {
        filtro = new IntentFilter( "com.example.intentservice.intent.action.TIMER_ACT");
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiverTimer,filtro);
    }

    private void configurarBroadcastReciever() {
        filtro = new IntentFilter( "com.example.intentservice.intent.action.RESPUESTA_OPERACION");
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiverReg,filtro);
    }

    private View.OnClickListener HandlerBtnRegistro = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(UserLogin.this, UserRegister.class);
            stopService(new Intent(UserLogin.this, Timer.class));
            startActivity(intent);
        }
    };

    private View.OnClickListener HandlerBtnLogIn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            presenter.loguearse(txtEmail.getText().toString(),txtPssw.getText().toString());
           // Intent intent = new Intent(UserLogin.this, UserRegister.class);
        }
    };



    @Override
    public Context getContexto() {
        return  getApplicationContext();
    }

    @Override
    public Object getSystemService() {
        return  getSystemService(Context.CONNECTIVITY_SERVICE);
    }



    public class ReceptorOperacion extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {


            try {
                String datosJsonString  = intent.getStringExtra("datosJson");
                JSONObject datosJson = new JSONObject(datosJsonString);

                Log.i("LOGUEO_MAIN","Datos Json Main Thread"+ datosJsonString);

                //txtResultado.setText(datosJsonString);
                Toast.makeText(getApplicationContext(),"Se recibido respuesta del server",Toast.LENGTH_SHORT).show();

//                String token  =datosJson.getString("token");
//                Log.i("LOGUEO_MAIN","TOKEN MAIN TRHEAD"+ token);

                String resultadoRequest = datosJson.getString("success");


                if(resultadoRequest.equals("true")){
                    token  =datosJson.getString("token");
                    tokenRefresh = datosJson.getString("token_refresh");

                    presenter.registrarEvento("TEST","EVENTO_LOGUEO","El usuario se logueo en el sistema",token);
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public class RecpetorEvento extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {


            try {
                String datosJsonString  = intent.getStringExtra("datosJson");
                JSONObject datosJson = new JSONObject(datosJsonString);

                Log.i("LOGUEO_MAIN","Datos Json Main Thread"+ datosJsonString);

                //txtResultado.setText(datosJsonString);
                Toast.makeText(getApplicationContext(),"Se recibido respuesta del server",Toast.LENGTH_SHORT).show();
                String resultadoRequest = datosJson.getString("success");

                if(resultadoRequest == "true"){

                    Log.i("LOGUEO_MAIN","TOKEN MAIN TRHEAD"+ token);

                    startService(new Intent(UserLogin.this, Timer.class));
                    Intent i = new Intent(UserLogin.this, Menu.class);
                    i.putExtra("token",token);
                    i.putExtra("token_refresh",tokenRefresh);
                    i.putExtra("user", txtEmail.getText().toString());
                    startActivity(i);

                }else
                {
                    String msg  =datosJson.getString("msg");
                    Log.i("LOGUEO_MAIN", msg);
                    Toast.makeText(getApplicationContext(),msg,Toast.LENGTH_SHORT).show();
                }




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }



    public class ReceptorTimer extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {
                String finTimer  = intent.getStringExtra("Fin");
            Toast.makeText(getApplicationContext(),"Se termino el tiempo",Toast.LENGTH_SHORT).show();



            //cierra el servicio ya que no es necesario mantenerlo, sera creado al pulsar el boton nuevamente
            stopService(new Intent(UserLogin.this, Timer.class));

            startService(new Intent(UserLogin.this, Timer.class));


        }
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
    {//Actualizar token y reempezar timer
        Ini_Sensores();

        super.onRestart();
    }

    @Override
    protected void onResume()
    {//Actualizar token y reempezar timer
        super.onResume();

        Ini_Sensores();
    }




}