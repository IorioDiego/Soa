package com.example.covid;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.covid.views.UserLogin;

import org.json.JSONException;
import org.json.JSONObject;

public class UserRegister extends AppCompatActivity implements IUserRegister.View {

    private static final String URI_REGISTER_USER = "http://so-unlam.net.ar/api/api/register";

    private EditText txtName;
    private EditText txtEnv;
    private EditText txtPassword;
    private EditText txtLastName;
    private EditText txtDni;
    private EditText txtEmail;
    private EditText txtComission;
    private EditText txtGroup;



    private Button btnReg;
    public IntentFilter filtro;
    private ReceptorOperacion receiverReg = new ReceptorOperacion();
    private IUserRegister.Presenter presenter;

    String token;
    String tokenRefresh; 

    private static final String  DATOS_JSON_KEY = "datosJson",
            SUCCESS_KEY = "success",
            AVISO  = "Usuario Registrado con Exito",
            TRUE  = "true",
            MSG_KEY  = "msg",
            SUBSCRIBE_RESPUESTA_OPERACION = "com.example.intentservice.intent.action.RESPUESTA_OPERACION",
            ENV  ="PROD",
            ERROR_CONEXION = "Error de conexion a la red";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = (IUserRegister.Presenter) new UserRegisterPresenter(this);
        //txtEnv = (EditText) findViewById(R.id.editTextEnv);
        txtName = (EditText) findViewById(R.id.editTextName);
        txtLastName = (EditText) findViewById(R.id.editTextLastName);
        txtDni = (EditText) findViewById(R.id.editTextDNI);
        txtEmail = (EditText) findViewById(R.id.editTextEmail);
        txtPassword = (EditText) findViewById(R.id.editTexPassword);
        txtComission = (EditText) findViewById(R.id.editTextCom);
        txtGroup = (EditText) findViewById(R.id.editTextGroup);

        btnReg = (Button) findViewById(R.id.buttonReg);
        btnReg.setOnClickListener(HandlerBtnReg);

    }

    private void configurarBroadcastReciever() {
        filtro = new IntentFilter( SUBSCRIBE_RESPUESTA_OPERACION);
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiverReg, filtro);
    }

    private View.OnClickListener HandlerBtnReg = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            ConnectivityManager connectivityManager = (ConnectivityManager)  getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {

                presenter.registrarse(ENV, txtName.getText().toString(), txtLastName.getText().toString(), txtDni.getText().toString(),
                        txtEmail.getText().toString(), txtPassword.getText().toString(), txtComission.getText().toString(), txtGroup.getText().toString());

            }else
            {
                Toast.makeText(getApplicationContext(), ERROR_CONEXION, Toast.LENGTH_SHORT).show();
            }
        }
    };



    public class ReceptorOperacion extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {


            try {
                String datosJsonString = intent.getStringExtra(DATOS_JSON_KEY);
                JSONObject datosJson = new JSONObject(datosJsonString);

                String resultadoRequest = datosJson.getString(SUCCESS_KEY);


                if (resultadoRequest.equals(TRUE)) {
                    Toast.makeText(getApplicationContext(), AVISO, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(UserRegister.this, UserLogin.class);

                    startActivity(i);


                } else {
                    String msg = datosJson.getString(MSG_KEY);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(receiverReg);

        super.onStop();

    }

    @Override
    protected void onRestart() {
        configurarBroadcastReciever();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        configurarBroadcastReciever();
        super.onResume();

    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

}