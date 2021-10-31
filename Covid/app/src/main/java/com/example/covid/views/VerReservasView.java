package com.example.covid.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import java.util.List;

public class VerReservasView extends AppCompatActivity implements IVerReservas.View {

    Button verReservas;
    String user;
    private IVerReservas.Presenter presenter;
    private static final String URI_REGISTER_EVENT = "http://so-unlam.net.ar/api/api/event";
    private Thread threadVerReservas = null;
    TableLayout table;
    String userTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_reservas);

        table = (TableLayout) findViewById(R.id.tableReservas);
        presenter  = (IVerReservas.Presenter) new VerReservasPresenter(this);
        verReservas = (Button) findViewById(R.id.consultaReservas);
        verReservas.setOnClickListener(handleBtnVerReservas);

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
            List<String> reservas = presenter.verReservas(userTxt);
            for (String n: reservas) {
                {
                    TableRow row=new TableRow(table.getContext());
                    TextView tv1=new TextView(table.getContext());
                    TextView tv2=new TextView(table.getContext());
                    tv1.setText("Fecha: ");
                    tv2.setText(n);
                    row.addView(tv1);
                    row.addView(tv2);
                    table.addView(row);


                    Toast.makeText(getContexto(), n, Toast.LENGTH_SHORT);
                }
            }
        }
    };

    @Override
    protected void onStop()
    {

        if(threadVerReservas != null)
            this.threadVerReservas.interrupt();

        super.onStop();
    }

    @Override
    protected void onDestroy()
    {
        if(threadVerReservas != null)
            this.threadVerReservas.interrupt();
        super.onDestroy();
    }


}