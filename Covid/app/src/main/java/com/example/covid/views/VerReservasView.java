package com.example.covid.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid.R;
import com.example.covid.ServiceHTTP_POST;
import com.example.covid.interfaces.IVerReservas;
import com.example.covid.presenters.VerReservasPresenter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class VerReservasView extends AppCompatActivity implements IVerReservas.View {

    Button verReservas;
    String user;
    private IVerReservas.Presenter presenter;
    private static final String URI_REGISTER_EVENT = "http://so-unlam.net.ar/api/api/event";
    private Thread threadVerReservas = null;
    TableLayout table;
    String userTxt;
    private TextView sinReservasTxt;
    private ListView listViewReservas;

    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_reservas);


        presenter = (IVerReservas.Presenter) new VerReservasPresenter(this);
        verReservas = (Button) findViewById(R.id.consultaReservas);
        verReservas.setOnClickListener(handleBtnVerReservas);
        listViewReservas = findViewById(R.id.listViewReservas);
        sinReservasTxt = findViewById(R.id.sinReservasTxtView);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userTxt = extras.getString("user");

    }

    @Override
    public Context getContexto() {
        return getApplicationContext();
    }

    @Override
    public Object getSystemService() {
        return null;
    }

    private View.OnClickListener handleBtnVerReservas = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            List<String> reservas = presenter.verReservas(userTxt, getApplicationContext());

            List<String> listaNueva = new ArrayList<String>();
            for (String n : reservas) {

                String[] partesFecha = n.split(" ");
                String nuevaFecha = partesFecha[0] + " " + partesFecha[1] + " " + partesFecha[2] + " " + partesFecha[3];
                listaNueva.add(nuevaFecha);
            }
            if (listaNueva.isEmpty()) {
                Toast.makeText(getApplicationContext(), "No se hicieron Reservas", Toast.LENGTH_SHORT).show();
                sinReservasTxt.setText("No hay reservas registradas");
            } else {
                adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, listaNueva);
                listViewReservas.setAdapter(adapter);
            }

        }
    };

    @Override
    protected void onStop() {


        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


}