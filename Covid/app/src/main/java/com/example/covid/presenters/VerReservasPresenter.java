package com.example.covid.presenters;

import android.widget.Toast;

import com.example.covid.interfaces.IVerReservas;
import com.example.covid.models.ReservaModel;

import java.util.List;

public class VerReservasPresenter implements IVerReservas.Presenter {

    private IVerReservas.View view;
    private ReservaModel model;

    public VerReservasPresenter(IVerReservas.View view){
        this.view = view;
        model = new ReservaModel();
    }

    @Override
    public void verReservas(String user) {


        List<String> dates = model.getReservas(user, view.getContexto());
        for (String n: dates) {
            Toast.makeText(view.getContexto(), "RESERVAS :"+ n, Toast.LENGTH_SHORT).show();
        }
    }

}
