package com.example.covid.presenters;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.method.Touch;
import android.widget.Toast;

import com.example.covid.interfaces.IReservas;
import com.example.covid.interfaces.IUserLogin;
import com.example.covid.models.ReservaModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReservasPresenter implements IReservas.Presenter{
    private IReservas.View view;
    private ReservaModel model;
    private static final String URI_LOGIN_USER = "http://so-unlam.net.ar/api/api/login";



    public ReservasPresenter(IReservas.View view){
        this.view = view;
    }

    @Override
    public void reservar(Date fechaSeleccionada, String cliente) {
        model = new ReservaModel();
        model.makeReservation(cliente, fechaSeleccionada, view.getContexto());
    }

}
