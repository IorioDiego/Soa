package com.example.covid.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import com.example.covid.R;
import com.example.covid.interfaces.IReservas;
import com.example.covid.presenters.ReservasPresenter;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReservasView1 extends AppCompatActivity implements IReservas.View {


    private IReservas.Presenter presenter;
    private Button reservarButton;
    private CalendarView calendarView;
    private Date currentDate;
    private String userTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservas);
        presenter  = (IReservas.Presenter) new ReservasPresenter(this);
        calendarView = (CalendarView) findViewById(R.id.calendarReserva);
        reservarButton = (Button) findViewById(R.id.reservarButton);

        calendarView.setOnDateChangeListener(handleDateChanged);
        reservarButton.setOnClickListener(handleBtnReservar);

        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");
        currentDate = new Date();

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        userTxt = extras.getString("user");
    }

    private View.OnClickListener handleBtnReservar = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            presenter.reservar(currentDate, userTxt);
        }
    };

    public CalendarView.OnDateChangeListener handleDateChanged = new CalendarView.OnDateChangeListener(){
        @Override
        public void onSelectedDayChange(CalendarView view, int year, int month,
                                        int dayOfMonth) {
            currentDate = new Date(year, month, dayOfMonth);
            Toast.makeText(getContexto(), "Fecha elegida: ",Toast.LENGTH_SHORT).show();
            Toast.makeText(getContexto(), currentDate.toString(),Toast.LENGTH_SHORT).show();
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
}