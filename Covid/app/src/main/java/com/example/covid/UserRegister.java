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
    private EditText txtResultado;


    private Button btnReg;

    public  IntentFilter filtro;

    private ReceptorOperacion receiverReg = new ReceptorOperacion();

    private IUserRegister.Presenter presenter;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter  = (IUserRegister.Presenter) new UserRegisterPresenter(this);
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
        filtro = new IntentFilter( "com.example.intentservice.intent.action.RESPUESTA_OPERACION");
        filtro.addCategory(Intent.CATEGORY_DEFAULT);
        registerReceiver(receiverReg,filtro);
    }

    private View.OnClickListener HandlerBtnReg = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            registrarse( txtEnv.getText().toString() ,
                    txtName.getText().toString(),
                    txtLastName.getText().toString(),
                    txtDni.getText().toString(),
                    txtEmail.getText().toString(),
                    txtPassword.getText().toString(),
                    txtComission.getText().toString(),
                    txtGroup.getText().toString());

       /*     JSONObject obj = new JSONObject();
            try {
                obj.put("env",txtEnv.getText().toString());
                obj.put("name",txtName.getText().toString());
                    obj.put("lastname",txtLastName.getText().toString());
                obj.put("dni",txtDni.getText().toString());
                obj.put("email",txtEmail.getText().toString());
                obj.put("password",txtPassword.getText().toString());
                obj.put("commission",txtComission.getText().toString());
                obj.put("group",txtGroup.getText().toString());//Integer.parseInt(txtGroup.getText().toString())


              //  Intent i = new Intent(UserRegister.this,ServiceHTTP_POST.class);
               // i.putExtra("uri",URI_REGISTER_USER);
               // i.putExtra("datosJson",obj.toString());

               // startService(i);

            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }
    };

    @Override
    public void registrarse(String environment, String name, String lastname, String dni, String email, String pssw, String com, String group) {
        presenter.registrarse(environment,name,lastname,dni,email,pssw,com,group);
    }

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


                String token  =datosJson.getString("token");
                Log.i("LOGUEO_MAIN","TOKEN MAIN TRHEAD"+ token);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}