package com.example.covid.presenters;

import android.content.Context;
import android.content.Intent;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covid.ServiceHTTP_POST;
import com.example.covid.interfaces.IReservas;
import com.example.covid.interfaces.IVerReservas;
import com.example.covid.models.ReservaModel;
import com.example.covid.models.VerReservasModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class VerReservasPresenter implements IVerReservas.Presenter {

    private IVerReservas.View view;
    private IVerReservas.Model model;
    public VerReservasPresenter(IVerReservas.View view){
        this.view = view;
        model = new VerReservasModel(this);
    }

    @Override
    public List<String> verReservas(String user,Context c) {
        List<String> dates = model.getReservas(user,c);
        return dates;
    }

}
