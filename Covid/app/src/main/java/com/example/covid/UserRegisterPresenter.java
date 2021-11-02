package com.example.covid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class UserRegisterPresenter implements IUserRegister.Presenter {

    private IUserRegister.View view;
    private IUserRegister.Model model;
    private static final String URI_REGISTER_USER = "http://so-unlam.net.ar/api/api/register";
    private static final String URI_REGISTER_EVENT = "http://so-unlam.net.ar/api/api/event";
    // public IntentFilter filtro;
    //  private ReceptorOperacion receiverReg = new ReceptorOperacion();

    public UserRegisterPresenter(IUserRegister.View view) {
        this.view = view;
    }

  /*  @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        configurarBroadcastReciever();
    }*/

    @Override
    public void registrarse(String environment, String name, String lastname, String dni, String email, String pssw, String com, String group) {
        ConnectivityManager connectivityManager = (ConnectivityManager) view.getSystemService();
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            JSONObject obj = new JSONObject();
            try {

                obj.put("env", environment);
                obj.put("name", name);
                obj.put("lastname", lastname);
                obj.put("dni", dni);
                obj.put("email", email);
                obj.put("password", pssw);
                obj.put("commission", com);
                obj.put("group", group);//Integer.parseInt(txtGroup.getText().toString())

                Intent i = new Intent((Context) view, ServiceHTTP_POST.class);
                i.putExtra("evento", "log");
                i.putExtra("uri", URI_REGISTER_USER);
                i.putExtra("datosJson", obj.toString());

                ((Context) view).startService(i);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        } else {

            Toast.makeText(view.getContexto(), "Error de conexion a la red", Toast.LENGTH_SHORT).show();

        }


    }
/*
    @Override
    public void registrarEvento(String env, String event, String desc,String token) {

        JSONObject obj = new JSONObject();
        try {

            obj.put("env", env);
            obj.put("type_events", event);
            obj.put("description", desc);


            Intent i = new Intent((Context) view,ServiceHTTP_POST.class);
            i.putExtra("evento","RegistrarEvento");
            i.putExtra("uri",URI_REGISTER_EVENT);
            i.putExtra("datosJson",obj.toString());
            i.putExtra("tokenEvento",token);

            ((Context) view).startService(i);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
/*

    private void configurarBroadcastReciever() {
        filtro = new IntentFilter( "com.example.intentservice.intent.action.RESPUESTA_OPERACION");
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiverReg,filtro);
    }
*/

/*
    public class ReceptorOperacion extends BroadcastReceiver
    {


        @Override
        public void onReceive(Context context, Intent intent) {


            try {
                String datosJsonString  = intent.getStringExtra("datosJson");
                JSONObject datosJson = new JSONObject(datosJsonString);

                Log.i("LOGUEO_MAIN","Datos Json Main Thread"+ datosJsonString);

                //txtResultado.setText(datosJsonString);
                Toast.makeText(context.getApplicationContext(),"Se recibido respuesta del server",Toast.LENGTH_SHORT).show();
               *//* String token  =datosJson.getString("TOKEN");
                Log.i("LOGUEO_MAIN","TOKEN MAIN TRHEAD"+ token);*//*

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }*/


}
