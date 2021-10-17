package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static final String URI_REGISTER_USER = "http://so-unlam.net.ar/api/api/register";

    private EditText txtName;
    private EditText txtEnv;
    private EditText txtPassword;
    private EditText txtLastName;

    private EditText txtDni;
    private EditText txtEmail;
    private EditText txtComission;
    private EditText txtGroup;
    private EditText txtResultado;


    private Button btnReg;

    public  IntentFilter filtro;

    private ReceptorOperacion receiverReg = new ReceptorOperacion();







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtEnv = (EditText) findViewById(R.id.editTextEnv);
       txtName = (EditText) findViewById(R.id.editTextName);
        txtLastName = (EditText) findViewById(R.id.editTextLastName);
        txtDni = (EditText) findViewById(R.id.editTextDNI);
        txtEmail = (EditText) findViewById(R.id.editTextEmail);
         txtPassword = (EditText) findViewById(R.id.editTexPassword);
        txtComission = (EditText) findViewById(R.id.editTextCom);
        txtGroup = (EditText) findViewById(R.id.editTextGroup);


        btnReg = (Button) findViewById(R.id.buttonReg);
        btnReg.setOnClickListener(HandlerBtnReg);

        configurarBroadcastReciever();

    }

    private void configurarBroadcastReciever() {
        filtro = new IntentFilter( "com.example.covid.intentservice.intent.action.RESPUESTA_OPERACION");
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiverReg,filtro);
    }

    private View.OnClickListener HandlerBtnReg = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            JSONObject obj = new JSONObject();
            try {
                obj.put("env",txtEnv.getText().toString());
                obj.put("name",txtName.getText().toString());
                obj.put("lastname",txtLastName.getText().toString());
                obj.put("dni",Integer.parseInt(txtDni.getText().toString()));
                obj.put("email",txtEmail.getText().toString());
                obj.put("password",txtPassword.getText().toString());
                obj.put("comission",txtComission.getText().toString());
                obj.put("group",11);//Integer.parseInt(txtGroup.getText().toString())

                Intent i = new Intent(MainActivity.this,ServiceHTTP_POST.class);
                i.putExtra("uri",URI_REGISTER_USER);
                i.putExtra("datosJson",obj.toString());

                startService(i);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };

    public class ReceptorOperacion extends BroadcastReceiver
    {


        @Override
        public void onReceive(Context context, Intent intent) {


            try {
                String datosJsonString  = intent.getStringExtra("datosJson");
                JSONObject datosJson = new JSONObject(datosJsonString);

                Log.i("LOGUEO_MAIN","Datos Json Main Thread"+ datosJsonString);

                txtResultado.setText(datosJsonString);
                Toast.makeText(getApplicationContext(),"Se recibido respuesta del server",Toast.LENGTH_SHORT).show();
               /* String token  =datosJson.getString("TOKEN");
                Log.i("LOGUEO_MAIN","TOKEN MAIN TRHEAD"+ token);*/

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}